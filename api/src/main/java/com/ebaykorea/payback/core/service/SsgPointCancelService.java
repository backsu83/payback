package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointOrigin;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.PaybackOperators;
import com.ebaykorea.payback.util.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.ebaykorea.payback.util.PaybackInstants.now;

//TODO: SRP를 위해 조회결과를 리턴하지 않도록
@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointCancelService {

  private final SsgPointStateDelegate ssgPointStateDelegate;
  private final SsgPointRepository ssgPointRepository;

  public SsgPointTargetResponseDto cancelPoint(final Long orderNo, final CancelSsgPointRequestDto request) {
    final var ssgPoints = ssgPointRepository.findAllByOrderNoAndSiteType(orderNo, request.getBuyerId(), request.getSiteType());

    // 취소 요청 데이터가 이미 있는지 여부
    final var maybeCanceledSsgPoint = ssgPoints.stream()
        .filter(SsgPointTargetResponseDto::isCancelType)
        .findAny();
    if (maybeCanceledSsgPoint.isPresent()) {
      return maybeCanceledSsgPoint.get();
    }

    // 적립 요청 데이터 여부
    final var maybeSavedSsgPoint = ssgPoints.stream()
        .filter(SsgPointTargetResponseDto::isSaveType)
        .findAny();

    final var local = PaybackOperators.operator(request.getBuyerId());

    // 적립 데이터가 없는 시점에 취소가 들어온 경우 예외 데이터 저장
    if (maybeSavedSsgPoint.isEmpty()) {
      ssgPointRepository.setCancelOrderNoNoneSave(
          SsgPointOrderNoDto.of(
              orderNo,
              request.getSiteType().getShortCode(),
              Instant.now(),
              local,
              Instant.now(),
              local));
      return null;
    }

    final var savedSsgPoint = maybeSavedSsgPoint.get();

    switch (PointStatusType.from(savedSsgPoint.getPointStatus())) {
      case Success:
        //적립건은 취소
        final var ssgPoint = createSsgPointWithCancelUnit(request, savedSsgPoint);
        log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(ssgPoint));
        return ssgPointRepository.save(ssgPoint).stream()
            .findAny()
            .orElse(null);
      case Ready:
        //대기건은 보류 처리
        final var withHoldSsgPoint = createSsgPointWithWithHold(request, savedSsgPoint);
        ssgPointRepository.updatePointStatus(withHoldSsgPoint);
        return ssgPointRepository.findByKey(orderNo, savedSsgPoint.getBuyerId(), request.getSiteType().getShortCode(), PointTradeType.Save.getCode())
            .orElse(null);
      default:
        return savedSsgPoint;
    }
  }

  private SsgPoint createSsgPointWithCancelUnit(final CancelSsgPointRequestDto request, final SsgPointTargetResponseDto ssgPointDto) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    final var ssgPointUnit = SsgPointUnit.cancelUnit(
        ssgPointDto.getOrderNo(),
        ssgPointDto.getPayAmount(),
        ssgPointDto.getSaveAmount(),
        now(), //취소는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointStrategy,
        SsgPointOrigin.builder()
            .orgApproveId(ssgPointDto.getPntApprId())
            .orgReceiptNo(ssgPointDto.getReceiptNo())
            .build(),
        request.getAdminId()
    );

    return createSsgPoint(request, ssgPointDto, ssgPointUnit);
  }

  private SsgPoint createSsgPointWithWithHold(final CancelSsgPointRequestDto request, final SsgPointTargetResponseDto ssgPointDto) {
    final var ssgPointStrategy = ssgPointStateDelegate.find(request.getSiteType());

    final var ssgPointUnit = SsgPointUnit.withHoldUnit(
        ssgPointDto.getOrderNo(),
        ssgPointDto.getPayAmount(),
        ssgPointDto.getSaveAmount(),
        now(), //보류는 현재날짜 (yyyy-mm-dd)
        true,
        ssgPointStrategy,
        null,
        request.getAdminId()
    );

    return createSsgPoint(request, ssgPointDto, ssgPointUnit);
  }

  private SsgPoint createSsgPoint(final CancelSsgPointRequestDto request, final SsgPointTargetResponseDto ssgPointDto, SsgPointUnit ssgPointUnit) {
    return SsgPoint.of(
        ssgPointDto.getPackNo(),
        ssgPointDto.getBuyerId(),
        ssgPointDto.getOrderDate(),
        request.getSiteType(),
        List.of(ssgPointUnit));
  }

}
