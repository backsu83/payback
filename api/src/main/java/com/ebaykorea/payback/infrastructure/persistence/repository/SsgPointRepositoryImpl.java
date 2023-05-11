package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointDailyVerifyEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointOrderNoEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointDailyVerifyRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointOrderNoRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.google.common.collect.Lists;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.util.PaybackStrings.EMPTY;
import static com.ebaykorea.payback.util.PaybackStrings.YES;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointRepositoryImpl implements SsgPointRepository {

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SsgPointOrderNoRepository ssgPointOrderNoRepository;

  private final SsgPointTargetEntityMapper ssgPointTargetEntityMapper;
  private final SsgPointOrderNoEntityMapper ssgPointOrderNoEntityMapper;

  private final SsgPointDailyVerifyRepository ssgPointDailyVerifyRepository;
  private final SsgPointDailyVerifyEntityMapper ssgPointDailyVerifyEntityMapper;

  @Transactional
  @Override
  public List<SsgPointTarget> save(final SsgPoint ssgPoint) {
    if (!ssgPoint.getIsMember()) {
      log.info("is not ssg member [{}][{}]", ssgPoint.getBuyerNo() , ssgPoint.getPackNo());
      return Collections.emptyList();
    }

    return ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .map(unit -> saveSsgTarget(ssgPoint, unit))
        .collect(Collectors.toUnmodifiableList());
  }

  @Transactional
  @Override
  public List<SsgPointTarget> cancel(SsgPoint ssgPoint) {
    //기존 적립건 cancelYn update
    ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .forEach(unit -> ssgPointTargetRepository.updateCancelYn(
            unit.getOrderNo(),
            ssgPoint.getBuyerNo(),
            ssgPoint.getOrderSiteType().getShortCode(),
            PointTradeType.Save.getCode(), //기존 적립건
            YES,
            unit.getAdminId(),
            unit.getAdminId(),
            Instant.now()
            ));
    //취소 데이터 입력
    return save(ssgPoint);
  }

  private SsgPointTarget saveSsgTarget(final SsgPoint ssgPoint, final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
  }

  @Override
  public void setPointStatus(final SsgPoint ssgPoint) {
    ssgPoint.getSsgPointUnits()
        .forEach(ssgPointUnit -> ssgPointTargetRepository.updatePointStatus(
            ssgPointUnit.getPointStatus().getStatusType().getCode(),
            ssgPointUnit.getAdminId(),
            ssgPointUnit.getAdminId(),
            ssgPointUnit.getScheduleDate(),
            ssgPointUnit.getOrderNo(),
            ssgPoint.getBuyerNo(),
            ssgPoint.getOrderSiteType().getShortCode(),
            ssgPointUnit.getPointStatus().getTradeType().getCode()
        ));
  }

  @Override
  public int retryFailedPointStatus(SsgPointRequestKey key, String manualOprt, Instant updateDate) {
    return ssgPointTargetRepository.retryFailPointStatus(
        PointStatusType.Ready.getCode(),
        0L,
        manualOprt,
        updateDate,
        key.getOrderNo(),
        key.getBuyerId(),
        key.getSiteType().getShortCode(),
        key.getPointTradeType().getCode(),
        PointStatusType.Fail.getCode());
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<SsgPointTarget> findByKey(final SsgPointRequestKey key) {
    return ssgPointTargetRepository.findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(key.getOrderNo(), key.getBuyerId(), key.getSiteType().getShortCode(), key.getPointTradeType().getCode())
        .map(ssgPointTargetEntityMapper::mapToSsgTarget);
  }

  @Override
  public void saveExceptOrderNo(SsgPointOrderNoDto ssgPointOrderNoDto) {
    ssgPointOrderNoRepository.save(ssgPointOrderNoEntityMapper.map(ssgPointOrderNoDto));
  }

  @Override
  public boolean hasAlreadySaved(final Long packNo, final String buyerId, final OrderSiteType siteType) {
    return !ssgPointTargetRepository.findAllByPackNoAndBuyerIdAndSiteTypeAndTradeType(
        packNo, buyerId, siteType.getShortCode(), PointTradeType.Save.getCode()).isEmpty();
  }

  @Override
  public List<SsgPointTarget> findAllByOrderNoAndSiteType(final Long orderNo, final OrderSiteType siteType) {
    return ssgPointTargetRepository.findAllByOrderNoAndSiteType(orderNo, siteType.getShortCode()).stream()
        .map(ssgPointTargetEntityMapper::mapToSsgTarget)
        .collect(Collectors.toUnmodifiableList());
  }
  
  @Transactional
  @Override
  public VerifyDailySsgPointDto verifyDailyPoint(VerifyDailySsgPointDto verifyDailySsgPointDto) {
    return ssgPointDailyVerifyEntityMapper.map(
            ssgPointDailyVerifyRepository.save(ssgPointDailyVerifyEntityMapper.map(verifyDailySsgPointDto))
    );
  }
}
