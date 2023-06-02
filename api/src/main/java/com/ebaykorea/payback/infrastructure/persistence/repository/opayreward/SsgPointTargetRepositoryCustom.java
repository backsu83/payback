package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward;

import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey;

import java.time.Instant;

public interface SsgPointTargetRepositoryCustom {
 long retryFailResponseCode(final Long tryCount,
     final String manualOprt,
     final Instant updateDate,
     final SsgPointRequestKey key);

 long updatePointStatus(final String pointStatus,
     final String manualOprt,
     final String updateOperator,
     final Instant updateDate,
     final Long orderNo,
     final String buyerId,
     final String siteType,
     final String tradeType);

 void updateCancelYn(final long orderNo,
     final String buyerId,
     final String siteType,
     final String tradeType,
     final String cancelYn,
     final String manualOprt,
     final String updateOperator,
     final Instant updateDate);
}
