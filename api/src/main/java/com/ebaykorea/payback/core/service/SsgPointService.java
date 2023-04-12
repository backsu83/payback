package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.dto.UpdateSsgPointTradeStatusRequestDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.PaybackOperators;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_UTC_FORMATTER;

//TODO: core 패키지안에 있는것이 맞을지 고민
//TODO: SRP를 위해 조회결과를 리턴하지 않도록
//TODO: 옥션과 지마켓 프로세스 통일 필요
@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  public SsgPointTargetResponseDto earnPoint(final SaveSsgPointRequestDto request) {

    final var maybeSsgPointSaveTarget = ssgPointRepository.findByKey(
        request.getOrderNo(),
        request.getBuyerId(),
        request.getSiteType().getShortCode(),
        PointTradeType.Save.getCode());
    if (maybeSsgPointSaveTarget.isPresent()) {
      return maybeSsgPointSaveTarget.get();
    }

    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    final var ssgPointUnit = SsgPointUnit.readyUnit(request.getOrderNo(),
        request.getPayAmount(),
        request.getSaveAmount(), //ssg point api 호출
        DATE_TIME_UTC_FORMATTER.parse(request.getScheduleDate(), Instant::from),
        true,
        ssgPointStrategy,
        null,
        request.getAdminId());

    final var ssgPoint = SsgPoint.of(request.getPackNo(),
        request.getBuyerId(),
        DATE_TIME_UTC_FORMATTER.parse(request.getOrderDate(), Instant::from),
        request.getSiteType(),
        Lists.newArrayList(ssgPointUnit));
    log.info("domain entity earn ssgPoint: {}", GsonUtils.toJson(ssgPoint));

    return ssgPointRepository.save(ssgPoint).stream()
        .findAny()
        .orElse(null);
  }

  //TODO
  public SsgPointTargetResponseDto retryFailPointStatus(final Long orderNo, final UpdateSsgPointTradeStatusRequestDto request) {
    final var local = PaybackOperators.operator(request.getBuyerId());
    final var updateCount = ssgPointRepository.retryFailPointStatus(request.getAdminId(),
        local, Instant.now(), orderNo, request.getBuyerId(), request.getSiteType().getShortCode(), request.getTradeType());
    if (updateCount > 0) {
      return ssgPointRepository.findByKey(orderNo, request.getBuyerId(), request.getSiteType().getShortCode(), request.getTradeType())
          .orElse(null);
    }
    return null;
  }
}
