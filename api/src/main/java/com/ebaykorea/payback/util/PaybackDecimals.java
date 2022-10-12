package com.ebaykorea.payback.util;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class PaybackDecimals {
  public static boolean isGreaterThanZero(final BigDecimal decimal) {
    return decimal.compareTo(ZERO) > 0;
  }
}
