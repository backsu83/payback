package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointOrderNoEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointOrderNoRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.util.PaybackStrings.YES;

@Service
@RequiredArgsConstructor
public class SsgPointRepositoryImpl implements SsgPointRepository {

  private final SsgPointTargetRepository ssgPointTargetRepository;
  private final SsgPointOrderNoRepository ssgPointOrderNoRepository;

  private final SsgPointTargetEntityMapper ssgPointTargetEntityMapper;
  private final SsgPointOrderNoEntityMapper ssgPointOrderNoEntityMapper;

  @Transactional
  @Override
  public List<SsgPointTargetResponseDto> save(final SsgPoint ssgPoint) {
    return ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .map(unit -> saveSsgTarget(ssgPoint, unit))
        .collect(Collectors.toUnmodifiableList());
  }

  @Transactional
  @Override
  public List<SsgPointTargetResponseDto> cancel(SsgPoint ssgPoint) {
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

  private SsgPointTargetResponseDto saveSsgTarget(final SsgPoint ssgPoint, final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
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

  @Override
  public List<SsgPointTargetResponseDto> findAllByOrderNoAndSiteType(final Long orderNo, final String buyerId, final OrderSiteType siteType) {
    return ssgPointTargetRepository.findAllByOrderNoAndBuyerIdAndSiteType(orderNo, buyerId, siteType.getShortCode()).stream()
        .map(ssgPointTargetEntityMapper::mapToSsgTarget)
        .collect(Collectors.toUnmodifiableList());
  }
}
