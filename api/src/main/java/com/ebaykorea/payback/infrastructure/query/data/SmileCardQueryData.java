package com.ebaykorea.payback.infrastructure.query.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class SmileCardQueryData {
  @Schema(description = "스마일카드 적립")
  BigDecimal smileCardCashbackAmount;
  @Schema(description = "T2 스마일카드 적립")
  BigDecimal t2SmileCardCashbackAmount;

  public static final SmileCardQueryData EMPTY = SmileCardQueryData.of(ZERO, ZERO);
}
