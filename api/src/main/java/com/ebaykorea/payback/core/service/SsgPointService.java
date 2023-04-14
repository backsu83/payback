package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.ssgpoint.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.ssgpoint.UpdateSsgPointTradeStatusRequestDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.PaybackOperators;
import com.ebaykorea.payback.util.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_UTC_FORMATTER;

//TODO: core 패키지안에 있는것이 맞을지 고민
//TODO: SRP를 위해 조회결과를 리턴하지 않도록

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  //TODO: 옥션과 지마켓 프로세스 통일 필요
  public SsgPointTarget earnPoint(final SaveSsgPointRequestDto request) {

    final var maybeSsgPointSaveTarget = ssgPointRepository.findByKey(request.key(request.getOrderNo()));
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
        List.of(ssgPointUnit));
    log.info("domain entity earn ssgPoint: {}", GsonUtils.toJson(ssgPoint));

    return ssgPointRepository.save(ssgPoint).stream()
        .findAny()
        .orElse(null);
  }

  public SsgPointTarget retryFailed(final Long orderNo, final UpdateSsgPointTradeStatusRequestDto request) {
    final var local = PaybackOperators.operator(request.getBuyerId());
    final var updateCount = ssgPointRepository.retryFailedPointStatus(request.key(orderNo), request.getAdminId(), local, Instant.now());
    if (updateCount > 0) {
      return ssgPointRepository.findByKey(request.key(orderNo))
          .orElse(null);
    }
    return null;
  }
}
