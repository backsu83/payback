package com.ebaykorea.payback.core.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaveSsgPointRequestDto {

  private OrderSiteType siteType;
  private Long packNo;
  private String buyerId;
  private Long orderNo;
  private BigDecimal payAmount;
  private String orderDate;
  private String scheduleDate;
}
