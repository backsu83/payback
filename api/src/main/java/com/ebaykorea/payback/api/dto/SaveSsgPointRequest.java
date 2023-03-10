package com.ebaykorea.payback.api.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSsgPointRequest {

  private String orderNo;
  private String txKey;
  private String packNo;
  private String buyerId;
  private OrderSiteType siteType;
  private PointTradeType tradeType;
  //옥션
  private BigDecimal payAmount;
  private String orderDate;
  private String scheduleDate;
}
