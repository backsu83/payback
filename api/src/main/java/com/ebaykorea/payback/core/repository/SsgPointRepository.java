package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SsgPointRepository {

  List<SsgPointTarget> save(SsgPoint ssgPoint);
  List<SsgPointTarget> cancel(SsgPoint ssgPoint);

  void setPointStatus(final SsgPoint ssgPoint);

  int retryFailPointStatus(String manualOprt, String updateOperator, Instant updateDate, Long orderNo, String buyerId, String siteType, String tradeType);

  Optional<SsgPointTarget> findByKey(SsgPointRequestKey key);

  void saveExceptOrderNo(SsgPointOrderNoDto ssgPointOrderNoDto);

  boolean hasAlreadySaved(final Long packNo, final String buyerId, final OrderSiteType siteType);

  List<SsgPointTarget> findAllByOrderNoAndSiteType(Long orderNo, String buyerId, OrderSiteType siteType);
}
