package com.ebaykorea.payback.scheduler.domain.entity;

import lombok.Data;

@Data
public class PaybackBatchRecord {

  private String orderKey;
  private String txKey;
  private int responseCode;
  private String messageCode;
  private String status;
  private int retryCount;
}
