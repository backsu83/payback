package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.dto.SsgPointDto;
import com.ebaykorea.payback.core.dto.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.SsgPointTargetResponseDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SsgPointRepository {

  List<SsgPointTargetResponseDto> save(SsgPoint ssgPoint);

  SsgPointDto findByPointStatusReady(long orderNo , String buyerId, OrderSiteType siteType);

  void updatePointStatus(final SsgPoint ssgPoint);

  int retryFailPointStatus(String manualOprt, String updateOperator, Instant updateDate, Long orderNo, String buyerId, String siteType, String tradeType);

  Optional<SsgPointTargetResponseDto> findByKey(Long orderId, String buyerId, String siteType, String tradeType);

  void setCancelOrderNoNoneSave(SsgPointOrderNoDto ssgPointOrderNoDto);

  boolean hasAlreadySaved(Long packNo, OrderSiteType siteType);
  List<SsgPointTargetResponseDto> findAllByOrderNoAndSiteType(Long orderNo, String buyerId, OrderSiteType siteType);
}
