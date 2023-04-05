package com.ebaykorea.payback.batch.domain;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsgPointTargetDto {

  private Long orderNo;
  private String buyerId;
  private OrderSiteType siteType;
  private PointTradeType tradeType;
  private PointStatusType status;
  private String receiptNo;
  private String pntApprId;
  private BigDecimal saveAmount;
  private String pointToken;
  private String accountDate;
  private String requestDate;
  private String responseCode;

}
