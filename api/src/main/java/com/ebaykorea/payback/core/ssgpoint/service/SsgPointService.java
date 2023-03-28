package com.ebaykorea.payback.core.ssgpoint.service;

import static com.ebaykorea.payback.util.PaybackInstants.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.core.ssgpoint.state.SsgPointStateDelegate;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  public List<SsgPointTargetResponseDto> earnPoint(final SaveSsgPointRequestDto request) {

    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());
    final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo(),
        request.getPayAmount(),
        request.getSaveAmount(), //ssg point api 호출
        DATE_TIME_FORMATTER.parse(request.getScheduleDate() , Instant::from),
        true,
        ssgPointStrategy.ready(),
        null);

    final var ssgPoint = SsgPoint.of(request.getPackNo(),
        request.getBuyerId(),
        Instant.parse(request.getOrderDate()),
        request.getSiteType(),
        Lists.newArrayList(ssgPointUnit));
    log.info("domain entity earn ssgPoint: {}", GsonUtils.toJson(ssgPoint));
    return ssgPointRepository.save(ssgPoint);
  }

  public List<SsgPointTargetResponseDto> cancelPoint(final CancelSsgPointRequestDto request) {
    //적립데이터 조회
    final var entity =  ssgPointRepository.findByPointStatusReady(request.getOrderNo() ,
        request.getBuyerId(),
        request.getSiteType());

    //취소
    if(Objects.nonNull(entity)) {
      final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());
      final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo() ,
          entity.getPayAmount(),
          entity.getSaveAmount(),
          now(), //취소는 현재날짜 (yyyy-mm-dd)
          true,
          ssgPointStrategy.cancel(),
          SsgPointOrigin.builder()
              .orgApproveId(entity.getPntApprId())
              .orgReceiptNo(entity.getReceiptNo())
              .build()
          );

      final var ssgPoint = SsgPoint.of(request.getPackNo(),
          entity.getBuyerId(),
          entity.getOrderDate(),
          request.getSiteType(),
          Lists.newArrayList(ssgPointUnit));
      log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(ssgPoint));
      return ssgPointRepository.save(ssgPoint);
    }

    /**
     * TODO 적립이 되기전에 취소 이벤트 처리
     * 상황 1 : 적립전 취소 - 포인트상태 RR
     * 상황 2 : 적립후 취소 - 포인트 데이터가 없는 상태
     */
    return Lists.newArrayList();
  }
}
