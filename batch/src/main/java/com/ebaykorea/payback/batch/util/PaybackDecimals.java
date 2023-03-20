package com.ebaykorea.payback.batch.util;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.stream.Collector;

public class PaybackDecimals {

  public static boolean isGreaterThanZero(final BigDecimal decimal) {
    return decimal.compareTo(ZERO) > 0;
  }

  public static BigDecimal orZero(final BigDecimal decimal) {
    return decimal == null ? ZERO : decimal;
  }

  // BigDecimal Sum
  public static Collector<BigDecimal, BigDecimal[], BigDecimal> summarizing() {
    return Collector.of(
        () -> new BigDecimal[] { ZERO }, // stateful container
        (result, decimal) -> result[0] = result[0].add(decimal),
        (lhs, rhs) -> {
          lhs[0] = lhs[0].add(rhs[0]);
          return lhs;
        },
        total -> total[0],
        Collector.Characteristics.UNORDERED
    );
  }
}
