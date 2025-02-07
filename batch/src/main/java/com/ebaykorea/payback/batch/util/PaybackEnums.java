package com.ebaykorea.payback.batch.util;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PaybackEnums {
  public static <V, E extends Enum<E>> Map<V, E> reverseMap(Class<E> eClass, Function<E, V> valueGetter) {
    final var map = new HashMap<V, E>(2 * eClass.getEnumConstants().length);
    for (E e : eClass.getEnumConstants()) {
      map.put(valueGetter.apply(e), e);
    }
    return unmodifiableMap(map);
  }
}
