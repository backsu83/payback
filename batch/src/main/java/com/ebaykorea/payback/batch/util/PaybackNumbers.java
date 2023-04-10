package com.ebaykorea.payback.batch.util;

public class PaybackNumbers {

  public static <T extends Number> int toInteger(T number) {
    return toInteger(number, 0);
  }

  public static <T extends Number> int toInteger(T number, Integer defaultValue) {
    if(number == null) return defaultValue;

    return number.intValue();
  }
}
