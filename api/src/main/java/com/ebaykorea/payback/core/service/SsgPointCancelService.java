package com.ebaykorea.payback.core.service;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.factory.ssgpoint.SsgPointCreater;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.util.PaybackOperators;
import com.ebaykorea.payback.util.support.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

//TODO: SRP를 위해 조회결과를 리턴하지 않도록
@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointCancelService {
  private final SsgPointRepository ssgPointRepository;
  private final SsgPointCreater ssgPointCreater;

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

    switch (PointStatusType.from(savedSsgPoint.getPointStatus())) {
      case Success:
        //적립건은 취소
        final var ssgPoint = ssgPointCreater.withCancelUnit(request, savedSsgPoint);
        log.info("domain entity cancel ssgPoint: {}", GsonUtils.toJson(ssgPoint));
        return ssgPointRepository.cancel(ssgPoint).stream()
            .findAny()
            .orElse(null);
      case Ready:
        //대기건은 보류 처리
        final var withHoldSsgPoint = ssgPointCreater.withWithholdUnit(request, savedSsgPoint);
        ssgPointRepository.setPointStatus(withHoldSsgPoint);
        return ssgPointRepository.findByKey(request.key(orderNo, savedSsgPoint.getBuyerId()))
            .orElse(null);
      default:
        return savedSsgPoint;
    }
  }
}
