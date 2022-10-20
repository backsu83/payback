package com.ebaykorea.payback.util;

import java.util.Optional;

public class PaybackObjects {
  public static <T> T orElse(T nullable, T defaultValue) {
    return nullable == null ? defaultValue : nullable;
  }
}
