package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SsgPointDto;
import com.ebaykorea.payback.core.dto.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointOrderNoEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointOrderNoRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SsgPointRepositoryImpl implements SsgPointRepository {

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SsgPointTargetEntityMapper ssgPointTargetEntityMapper;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  private final SsgPointOrderNoRepository ssgPointOrderNoRepository;

  private final SsgPointOrderNoEntityMapper ssgPointOrderNoEntityMapper;

  @Transactional
  @Override
  public List<SsgPointTargetResponseDto> save(final SsgPoint ssgPoint) {
    return ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .map(unit -> saveSsgTarget(ssgPoint, unit))
        .collect(Collectors.toUnmodifiableList());
  }

  private SsgPointTargetResponseDto saveSsgTarget(final SsgPoint ssgPoint, final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
  }

  @Transactional(readOnly = true)
  @Override
  public SsgPointDto findByPointStatusReady(final long orderNo, final String buyerId, final OrderSiteType siteType) {
    final var ssgPointTargetEntity = ssgPointTargetRepositorySupport.findByPointStatusReady(orderNo, buyerId, siteType);
    return ssgPointTargetEntityMapper.mapToPoint(ssgPointTargetEntity);
  }

  @Override
  public void updatePointStatus(final SsgPoint ssgPoint) {
    ssgPoint.getSsgPointUnits()
        .forEach(ssgPointUnit -> ssgPointTargetRepository.updateCancelStatus(
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
  public int retryFailPointStatus(String manualOprt, String updateOperator, Instant updateDate, Long orderNo, String buyerId, String siteType, String tradeType) {
    return ssgPointTargetRepository.retryFailPointStatus(PointStatusType.Ready.getCode(), 0L, manualOprt, updateOperator, updateDate, orderNo, buyerId, siteType, tradeType, PointStatusType.Fail.getCode());
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<SsgPointTargetResponseDto> findByKey(Long orderId, String buyerId, String siteType, String tradeType) {
    return ssgPointTargetRepository.findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(orderId, buyerId, siteType, tradeType)
        .map(ssgPointTargetEntityMapper::mapToSsgTarget);
  }

  @Override
  public void setCancelOrderNoNoneSave(SsgPointOrderNoDto ssgPointOrderNoDto) {
    ssgPointOrderNoRepository.save(ssgPointOrderNoEntityMapper.map(ssgPointOrderNoDto));
  }

  @Override
  public boolean hasAlreadySaved(final Long packNo, final OrderSiteType siteType) {
    return !ssgPointTargetRepository.findAllByPackNoAndSiteType(packNo, siteType.getShortCode()).isEmpty();
  }

  public List<SsgPointTargetResponseDto> findAllByOrderNoAndSiteType(final Long orderNo, final String buyerId, final OrderSiteType siteType) {
    return ssgPointTargetRepository.findAllByOrderNoAndSiteType(orderNo, buyerId, siteType.getShortCode()).stream()
        .map(ssgPointTargetEntityMapper::mapToSsgTarget)
        .collect(Collectors.toUnmodifiableList());
  }
}
