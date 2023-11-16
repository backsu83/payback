package com.ebaykorea.payback.infrastructure.query.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackTargetQueryData {
  @Schema(description = "캐시백 타입", example = "Seller, Item, SmilePay, ChargePay, ClubDay")
  private String cashbackType;
  @Schema(description = "적립 금액")
  private BigDecimal saveAmount;
  @Schema(description = "적립 예상일")
  private Instant expectSaveDate;
}
