package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.factory.ssgpoint.SsgPointCreater;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.support.GsonUtils;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//TODO: SRP를 위해 조회결과를 리턴하지 않도록
@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointCancelService {
  private final SsgPointRepository ssgPointRepository;
  private final SsgPointCreater ssgPointCreater;
  private final SsgPointState ssgPointState;

  public SsgPointTarget cancelPoint(final Long orderNo, final CancelSsgPointRequestDto request) {
    final var ssgPoints = ssgPointRepository.findAllByOrderNoAndSiteType(orderNo, request.getSiteType());

    // 취소 요청 데이터가 이미 있는지 여부
    final var maybeCanceledSsgPoint = ssgPoints.stream()
        .filter(SsgPointTarget::isCancelType)
        .findAny();
    if (maybeCanceledSsgPoint.isPresent()) {
      return maybeCanceledSsgPoint.get();
    }

    // 적립 요청 데이터 여부
    final var maybeSavedSsgPoint = ssgPoints.stream()
        .filter(SsgPointTarget::isSaveType)
        .findAny();

    // TODO: 비회원이나 ssg 비연동 고객인 경우 적립 데이터가 없을 수 있음 아래 로직은 추후 제거
    // 적립 데이터가 없는 시점에 취소가 들어온 경우 예외 데이터 저장
    if (maybeSavedSsgPoint.isEmpty()) {
      ssgPointRepository.saveExceptOrderNo(
          SsgPointOrderNoDto.of(
              orderNo,
              request.getSiteType().getShortCode(),
              Instant.now(),
              Instant.now()));
      return null;
    }
    final var savedSsgPoint = maybeSavedSsgPoint.get();
    final var cancelStatus = ssgPointState.cancelStatus(savedSsgPoint.getPointStatus() , savedSsgPoint.getScheduleDate());

    switch (cancelStatus) {
      case Cancel:
        final var cancelUnit = ssgPointCreater.withCancelUnit(savedSsgPoint, ssgPointState, cancelStatus, request.getAdminId());
        log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(cancelUnit));
        return ssgPointRepository.cancel(cancelUnit).stream()
            .findAny()
            .orElse(null);

      case CacnelReady:
        final var cancelReadyUnit = ssgPointCreater.withCancelUnit(savedSsgPoint, ssgPointState, cancelStatus, request.getAdminId());
        log.info("domain entity cancelReady ssgPoint: {}", GsonUtils.toJson(cancelReadyUnit));
        ssgPointRepository.cancel(cancelReadyUnit).stream()
            .findAny()
            .orElse(null);
        return ssgPointRepository.findByKey(request.key(orderNo, savedSsgPoint.getBuyerId()))
            .orElse(null);

      case WithHold:
        final var withHoldUnit = ssgPointCreater.withWithholdUnit(savedSsgPoint, ssgPointState, request.getAdminId());
        log.info("domain entity withHold ssgPoint: {}", GsonUtils.toJson(withHoldUnit));
        ssgPointRepository.setPointStatus(withHoldUnit);
        return ssgPointRepository.findByKey(request.key(orderNo, savedSsgPoint.getBuyerId()))
            .orElse(null);

      default:
        return savedSsgPoint;
    }
  }
}
