package com.ebaykorea.payback.util;

import java.util.Collections;
import java.util.List;

public class PaybackCollections {
  public static <T> List<T> orEmpty(final List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }
    return list;
  }
}
