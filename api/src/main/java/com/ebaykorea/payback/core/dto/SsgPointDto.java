package com.ebaykorea.payback.core.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class SsgPointDto {
  private Long orderNo;
  private String buyerId;
  private String siteType;
  private String tradeType;
  private String receiptNo;
  private String pntApprId;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private String pointStatus;
  private String pointToken;
  private Instant orderDate;
  private Instant scheduleDate;
  private Instant requestDate;
  private String accountDate;
  private String responseCode;
  private String trcNo;
  private String tradeNo;
  private Long packNo;
  private Instant insertDate;
  private String insertOperator;
  private Instant updateDate;
  private String updateOperator;
}
