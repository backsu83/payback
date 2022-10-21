package com.ebaykorea.payback.core.domain.entity.order;

import lombok.Value;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Value
public class KeyMap {
  String orderKey;
  long packNo;
  List<OrderUnitKey> orderUnitKeys;

  public static KeyMap of(
      final String orderKey,
      final long packNo,
      final List<OrderUnitKey> orderUnitKeys) {
    return new KeyMap(orderKey, packNo, orderUnitKeys);
  }

  public Map<String, OrderUnitKey> orderUnitKeyMap() {
    return orderUnitKeys.stream()
        .collect(toUnmodifiableMap(OrderUnitKey::getOrderUnitKey, identity()));
  }
}
