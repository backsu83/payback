package com.ebaykorea.payback.core.dto.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import lombok.Value;

@Value
public class SsgPointRequestKey {
  Long orderNo;
  String buyerId;
  OrderSiteType siteType;
  PointTradeType pointTradeType;
}
