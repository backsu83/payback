package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class SsgPointInfoDto {
  private BigDecimal ssgPointExpectSaveAmount;
  private Boolean isSsgPoint;
  private Integer ssgPointDatePlus;
}
