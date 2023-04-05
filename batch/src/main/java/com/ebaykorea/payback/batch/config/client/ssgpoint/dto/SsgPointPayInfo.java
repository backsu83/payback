package com.ebaykorea.payback.batch.config.client.ssgpoint.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsgPointPayInfo {
  private String payType;
  private BigDecimal payAmt;
  private String payGb;
}
