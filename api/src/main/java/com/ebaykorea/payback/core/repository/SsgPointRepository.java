package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.order.OrderUnitKey;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import com.ebaykorea.payback.core.dto.VerifyDailySsgPointDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SsgPointRepository {

  List<SsgPointTarget> save(SsgPoint ssgPoint);
  List<SsgPointTarget> cancel(SsgPoint ssgPoint);

  void setPointStatus(final SsgPoint ssgPoint);

  long retryFailResponseCode(SsgPointRequestKey key, String manualOprt, Instant updateDate);

  Optional<SsgPointTarget> findByKey(SsgPointRequestKey key);

  void saveExceptOrderNo(SsgPointOrderNoDto ssgPointOrderNoDto);

  boolean hasAlreadySaved(final List<OrderUnitKey> orderUnitKeys, final String buyerId, final OrderSiteType siteType);

  List<SsgPointTarget> findAllByOrderNoAndSiteType(Long orderNo, OrderSiteType siteType);

}
