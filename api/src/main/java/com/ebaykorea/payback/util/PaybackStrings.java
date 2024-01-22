package com.ebaykorea.payback.util;

import static com.ebaykorea.payback.util.PaybackObjects.isNull;

public class PaybackStrings {
  public static String EMPTY = "";
  public static String NONE = "N";
  public static String YES = "Y";

  public static boolean isBlank(final String str) {
    return str == null || str.isBlank();
  }

  public static boolean isNotBlank(final String str) {
    return !isBlank(str);
  }

  public static String orEmpty(final String nullable) {
    return isNull(nullable) ? EMPTY : nullable;
  }
}
