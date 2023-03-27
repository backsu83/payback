package com.ebaykorea.payback.batch.domain;

import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointPayInfo;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SsgPointProcesserDto {

  private Long orderNo;
  private String buyerId;
  private OrderSiteType siteType;
  private PointTradeType tradeType;
  private PointStatusType status;
  private String receiptNo;
  private BigDecimal payAmount;
  private String cancelYn;
  private String pointToken;
  private String trcNo;
  private String tradeNo;
}
