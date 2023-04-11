package com.ebaykorea.payback.batch.config.client.ssgpoint.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointPayInfo {
  private String payType;
  private BigDecimal payAmt;
  private String payGb;
}
