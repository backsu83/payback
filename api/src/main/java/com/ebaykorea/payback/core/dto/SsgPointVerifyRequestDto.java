package com.ebaykorea.payback.core.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsgPointVerifyRequestDto {
  private String reqDate;
  private PointTradeType tradeType;
  private OrderSiteType siteType;
}
