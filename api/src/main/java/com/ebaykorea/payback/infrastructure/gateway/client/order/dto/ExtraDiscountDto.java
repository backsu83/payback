package com.ebaykorea.payback.infrastructure.gateway.client.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ExtraDiscountDto {
  private String snapshotKey;
  private Long discountNo;
  private List<ExtraDiscountUnitDto> discountUnits;

  @Data
  public static class ExtraDiscountUnitDto {
    private String orderUnitKey;
    private BigDecimal discountAmount;
  }
}
