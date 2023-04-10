package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.*;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.Instant;
import java.util.*;

import static com.ebaykorea.payback.util.PaybackInstants.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;
import static java.util.Collections.emptyList;

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
        DATE_TIME_FORMATTER.parse(request.getScheduleDate(), Instant::from),
        true,
        ssgPointStrategy.ready(),
        null,
        request.getAdminId());

    final var ssgPoint = SsgPoint.of(request.getPackNo(),
        request.getBuyerId(),
        DATE_TIME_FORMATTER.parse(request.getOrderDate(), Instant::from),
        request.getSiteType(),
        Lists.newArrayList(ssgPointUnit));
    log.info("domain entity earn ssgPoint: {}", GsonUtils.toJson(ssgPoint));

    try {
      return ssgPointRepository.save(ssgPoint);
    } catch (Exception e) {
      return ssgPointRepository.findByKey(
              request.getOrderNo(),
              request.getBuyerId(),
              request.getSiteType().getShortCode(),
              PointTradeType.Save.getCode())
          .map(List::of)
          .orElse(emptyList());
    }
  }

  public List<SsgPointTargetResponseDto> cancelPoint(final Long packNo, final CancelSsgPointRequestDto request) {
    //적립데이터 조회
    final var entity = ssgPointRepository.findByPointStatusReady(request.getOrderNo(),
        request.getBuyerId(),
        request.getSiteType());

    var local = request.getBuyerId();
    try {
      local = InetAddress.getLocalHost().getHostName();
      if (local.length() > 50) {
        local = local.substring(0, 49);
      }
    } catch (Exception e) {
    }

    if (entity == null) {
      ssgPointRepository.setCancelOrderNoNoneSave(SsgPointOrderNoDto.of(request.getOrderNo(), request.getSiteType().getShortCode(),
          Instant.now(), local, Instant.now(), local));
      return emptyList();
    }

    //취소
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    switch (entity.getPointStatus()) {
      case "SS":
        final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo(),
            entity.getPayAmount(),
            entity.getSaveAmount(),
            now(), //취소는 현재날짜 (yyyy-mm-dd)
            true,
            ssgPointStrategy.cancel(),
            SsgPointOrigin.builder()
                .orgApproveId(entity.getPntApprId())
                .orgReceiptNo(entity.getReceiptNo())
                .build(),
            request.getAdminId()
        );
        final var ssgPoint = SsgPoint.of(packNo,
            entity.getBuyerId(),
            entity.getOrderDate(),
            request.getSiteType(),
            Lists.newArrayList(ssgPointUnit));
        log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(ssgPoint));
        return ssgPointRepository.save(ssgPoint);
      case "RR":
        ssgPointRepository.updatePointStatus(PointStatusType.WithHold.getCode(), request.getAdminId(), local,
            Instant.now(), request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode());
        return ssgPointRepository.findByKey(request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode())
            .map(List::of)
            .orElse(emptyList());
      default:
        return ssgPointRepository.findByKey(request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode())
            .map(List::of)
            .orElse(emptyList());
    }

  }

  public List<SsgPointTargetResponseDto> retryFailPointStatus(final UpdateSsgPointTradeStatusRequestDto request) {
    var local = request.getBuyerId();
    try {
      local = InetAddress.getLocalHost().getHostName();
      if (local.length() > 50) {
        local = local.substring(0, 49);
      }
    } catch (Exception e) {
    }

    final var updateCount = ssgPointRepository.retryFailPointStatus(request.getAdminId(),
        local, Instant.now(), request.getOrderNo(), request.getBuyerId(), request.getSiteType().getShortCode(), request.getTradeType());
    if (updateCount > 0) {
      return ssgPointRepository.findByKey(request.getOrderNo(), request.getBuyerId(), request.getSiteType().getShortCode(), request.getTradeType())
          .map(List::of)
          .orElse(emptyList());
    }
    return emptyList();
  }
}
