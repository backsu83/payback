package com.ebaykorea.payback.batch.util;


import static com.ebaykorea.payback.batch.util.PaybackObjects.orElse;

public class PaybackBooleans {
  public static boolean asPrimitive(final Boolean bool) {
    return asPrimitive(bool, false);
  }
  public static boolean asPrimitive(final Boolean bool, final boolean defaultValue) {
    return orElse(bool, defaultValue);
  }

  public static String toYN(final Boolean bool) { return asPrimitive(bool)? "Y" : "N"; }
}
