package com.ebaykorea.payback.infrastructure.persistence.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointUnit;
import com.ebaykorea.payback.core.dto.SsgPointCancedDto;
import com.ebaykorea.payback.core.dto.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;
import com.ebaykorea.payback.core.repository.SsgPointRepository;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointOrderNoEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.mapper.SsgPointTargetEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointOrderNoRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgPointTargetRepositorySupport;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

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
    List<SsgPointTargetResponseDto> sspointTargetList = Lists.newArrayList();
    ssgPoint.getSsgPointUnits().stream()
        .filter(SsgPointUnit::getIsPolicy)
        .forEach(unit->{
          sspointTargetList.add(saveSsgTarget(ssgPoint , unit));
    });
    return sspointTargetList;
  }

  private SsgPointTargetResponseDto saveSsgTarget(final SsgPoint ssgPoint ,final SsgPointUnit ssgPointUnit) {
    final var ssgPointTargetEntity = ssgPointTargetEntityMapper.map(ssgPoint, ssgPointUnit);
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.save(ssgPointTargetEntity));
  }

  @Transactional(readOnly = true)
  @Override
  public SsgPointCancedDto findByPointStatusReady(final long orderNo, final String buyerId , final OrderSiteType siteType) {
    final var ssgPointTargetEntity = ssgPointTargetRepositorySupport.findByPointStatusReady(orderNo, buyerId, siteType);
    return ssgPointTargetEntityMapper.mapToPointCancel(ssgPointTargetEntity);
  }

  @Override
  public int updatePointStatus(String pointStatus, @Nullable String manualOprt, String updateOperator, Instant updateDate, @NonNull Long orderNo, @NonNull String buyerId, @NonNull String siteType, @NonNull String tradeType) {
    return ssgPointTargetRepository.updateCancelStatus(pointStatus, manualOprt, updateOperator, updateDate, orderNo, buyerId, siteType, tradeType);
  }

  @Override
  public int retryFailPointStatus(String manualOprt, String updateOperator, Instant updateDate, Long orderNo, String buyerId, String siteType, String tradeType) {
    return ssgPointTargetRepository.retryFailPointStatus(PointStatusType.Ready.getCode(), 0L, manualOprt, updateOperator, updateDate,  orderNo,  buyerId,  siteType,  tradeType, PointStatusType.Fail.getCode());


  }

  @Transactional(readOnly = true)
  @Override
  public SsgPointTargetResponseDto findByKey(Long orderId, String buyerId, String siteType, String tradeType) {
    return ssgPointTargetEntityMapper.mapToSsgTarget(ssgPointTargetRepository.findFirstByOrderNoAndBuyerIdAndSiteTypeAndTradeType(orderId, buyerId, siteType, tradeType));
  }

  @Override
  public void setCancelOrderNoNoneSave(SsgPointOrderNoDto ssgPointOrderNoDto) {
    ssgPointOrderNoRepository.save(ssgPointOrderNoEntityMapper.map(ssgPointOrderNoDto));
  }
}
