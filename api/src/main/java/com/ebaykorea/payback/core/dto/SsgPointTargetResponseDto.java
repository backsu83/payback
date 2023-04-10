package com.ebaykorea.payback.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsgPointTargetResponseDto {
  private Long packNo;
  private Long orderNo;
  private String buyerId;
  private String pointStatus;
  private String tradeType;
  private String receiptNo;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private String scheduleDate;
  private String adminId;
}
