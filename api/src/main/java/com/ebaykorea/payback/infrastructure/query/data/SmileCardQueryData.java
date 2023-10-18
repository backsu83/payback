package com.ebaykorea.payback.infrastructure.query.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class SmileCardQueryData {
  BigDecimal smileCardCashbackAmount;
  BigDecimal t2SmileCardCashbackAmount;

  public static final SmileCardQueryData EMPTY = SmileCardQueryData.of(ZERO, ZERO);
}
