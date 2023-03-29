package com.ebaykorea.payback.batch.domain;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class SsgPointTargetDto {

  private Long orderNo;
  private String buyerId;
  private String siteType;
  private String tradeType;
  private String receiptNo;
  private String pntApprId;
  private BigDecimal saveAmount;
  private String status;
  private String pointToken;
  private String accountDate;
  private String requestDate;
  private String responseCode;

}
