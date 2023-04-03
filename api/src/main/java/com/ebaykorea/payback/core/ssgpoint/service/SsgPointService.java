package com.ebaykorea.payback.core.ssgpoint.service;

import static com.ebaykorea.payback.util.PaybackInstants.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.*;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.core.ssgpoint.state.SsgPointStateDelegate;
import com.ebaykorea.payback.util.support.GsonUtils;
import com.google.common.collect.Lists;

import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  public List<SsgPointTargetResponseDto> earnPoint(final SaveSsgPointRequestDto request) {

    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    var myOrderDate = LocalDateTime.parse(
                    request.getOrderDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            ).atZone(
                    ZoneOffset.UTC
            )
            .toInstant();

    var myScheduleDate = LocalDateTime.parse(
                    request.getOrderDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )
            .atZone(
                    ZoneOffset.UTC
            )
            .toInstant();


    final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo(),
        request.getPayAmount(),
        request.getSaveAmount(), //ssg point api 호출
            myScheduleDate,
        true,
        ssgPointStrategy.ready(),
        null,
            request.getAdminId());

    final var ssgPoint = SsgPoint.of(request.getPackNo(),
        request.getBuyerId(),
            myOrderDate,
        request.getSiteType(),
        Lists.newArrayList(ssgPointUnit));
    log.info("domain entity earn ssgPoint: {}", GsonUtils.toJson(ssgPoint));

    try {
      return ssgPointRepository.save(ssgPoint);
    } catch(Exception e) {
      List<SsgPointTargetResponseDto> result = new ArrayList<>();
      result.add(
      ssgPointRepository.findByKey(request.getOrderNo(), request.getBuyerId(),
              request.getSiteType().getShortCode(), PointTradeType.Save.getCode()));
      return result;
    }
  }

  public List<SsgPointTargetResponseDto> cancelPoint(final CancelSsgPointRequestDto request) {
    //적립데이터 조회
    final var entity =  ssgPointRepository.findByPointStatusReady(request.getOrderNo() ,
        request.getBuyerId(),
        request.getSiteType());

    var local = request.getBuyerId();
    try {
      local = InetAddress.getLocalHost().getHostName();
      if (local.length() > 50) {
        local = local.substring(0, 49);
      }
    }catch (Exception e) {
    }

    //취소
    if(Objects.nonNull(entity)) {
      final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());
      List<SsgPointTargetResponseDto> list = new ArrayList<>();
      switch (entity.getPointStatus()) {
        case "SS":
          final var ssgPointUnit = SsgPointUnit.of(request.getOrderNo() ,
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
          final var ssgPoint = SsgPoint.of(request.getPackNo(),
                  entity.getBuyerId(),
                  entity.getOrderDate(),
                  request.getSiteType(),
                  Lists.newArrayList(ssgPointUnit));
          log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(ssgPoint));
          return ssgPointRepository.save(ssgPoint);
        case "RR":
          var returnCode = ssgPointRepository.updatePointStatus(
                  PointStatusType.WithHold.getCode(), request.getAdminId(), local,
                  Instant.now(), request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode());
          list.add(ssgPointRepository.findByKey(request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode()));
          return list;
        default:
          list.add(ssgPointRepository.findByKey(request.getOrderNo(), entity.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode()));
          return list;
      }
    } else {
      ssgPointRepository.setCancelOrderNoNoneSave(SsgPointOrderNoDto.of(request.getOrderNo(), request.getSiteType().getShortCode(),
                      Instant.now(), local, Instant.now(), local));
    }
    return null;
  }

  public List<SsgPointTargetResponseDto> retryFailPointStatus(final UpdateSsgPointTradeStatusRequestDto request) {
    var local = request.getBuyerId();
    try {
      local = InetAddress.getLocalHost().getHostName();
      if (local.length() > 50) {
        local = local.substring(0, 49);
      }
    }catch (Exception e) {
    }
    var updateCount = ssgPointRepository.retryFailPointStatus(request.getAdminId(),
            local, Instant.now(), request.getOrderNo(),request.getBuyerId(),request.getSiteType().getShortCode(), request.getTradeType());
    List<SsgPointTargetResponseDto> list = new ArrayList<>();
    if (updateCount > 0) {
      list.add(ssgPointRepository.findByKey(request.getOrderNo(), request.getBuyerId(), request.getSiteType().getShortCode(), request.getTradeType()));
    }
    return list;
  }
}
