package com.ebaykorea.payback.util;

public class PaybackObjects {
  public static <T> T orElse(T nullable, T defaultValue) {
    return nullable == null ? defaultValue : nullable;
  }
}
