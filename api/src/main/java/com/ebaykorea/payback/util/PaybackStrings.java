package com.ebaykorea.payback.util;

import static com.ebaykorea.payback.util.PaybackObjects.isNull;

public class PaybackStrings {
  public static String EMPTY = "";
  public static String NONE = "N";
  public static String YES = "Y";

  public static boolean isBlank(String str) {
    return str == null || str.isBlank();
  }

  public static String orEmpty(final String nullable) {
    return isNull(nullable) ? EMPTY : nullable;
  }
}
