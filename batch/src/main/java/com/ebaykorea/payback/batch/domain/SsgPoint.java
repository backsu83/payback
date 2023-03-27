package com.ebaykorea.payback.batch.domain;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class SsgPoint {

  private Long orderNo;
  private String buyerId;
  private String siteType;
  private String tradeType;
  private String receiptNo;
  private String orgReceiptNo;
  private String pntApprId;
  private String orgPntApprId;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private String pointStatus;
  private String cancelYn;
  private String pointToken;
  private Instant orderDate;
  private Instant scheduleDate;
  private String responseCode;
  private String trcNo;
  private String tradeNo;
  private Long packNo;

}
