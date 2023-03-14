package com.ebaykorea.payback.core.ssgpoint.service;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SaveSsgPointRequestDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.core.ssgpoint.state.SsgPointStateDelegate;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  public List<SsgPointTargetResponseDto> setSsgPoint(SaveSsgPointRequestDto request) {

    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());
    final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo(),
        request.getPayAmount(),
        request.getPayAmount(), //ssg point api 호출
        request.getScheduleDate(),
        null,
        true,
        ssgPointStrategy.ready());

    final var ssgPoint = SsgPoint.of(request.getPackNo(),
        request.getBuyerId(),
        Instant.parse(request.getOrderDate()),
        request.getSiteType(),
        Lists.newArrayList(ssgPointUnit));
    log.info("ssgPoint: {}" , GsonUtils.toJsonPretty(ssgPoint));

    return ssgPointRepository.save(ssgPoint);
  }
}
