package com.ebaykorea.payback.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class PaybackCollections {
  public static <T> List<T> orEmpty(final List<T> list) {
    if (list == null) {
      return Collections.emptyList();
    }
    return list;
  }

  public static <T> Stream<T> orEmptyStream(final List<T> list) {
    if (list == null) {
      return Stream.empty();
    }
    return list.stream();
  }
}
