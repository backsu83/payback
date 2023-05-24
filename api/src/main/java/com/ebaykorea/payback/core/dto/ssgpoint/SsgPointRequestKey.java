package com.ebaykorea.payback.core.dto.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPointRequestKey {
  Long orderNo;
  String buyerId;
  OrderSiteType siteType;
  PointTradeType pointTradeType;
}
