package com.ebaykorea.payback.scheduler.service.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaybackBatchRecord {

  private String orderKey;
  private String txKey;
  private Long responseCode;
  private String message;
  private String status;
  private Long retryCount;
}
