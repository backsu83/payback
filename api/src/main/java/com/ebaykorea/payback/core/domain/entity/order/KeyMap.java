package com.ebaykorea.payback.core.domain.entity.order;

import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.Value;

import java.util.List;
import java.util.Map;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static com.ebaykorea.payback.util.PaybackCollections.orEmpty;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
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

  private KeyMap(
      final String orderKey,
      final long packNo,
      final List<OrderUnitKey> orderUnitKeys) {
    this.orderKey = orderKey;
    this.packNo = packNo;
    this.orderUnitKeys = orderUnitKeys;

    validate();
  }

  public void validate() {
    if (isBlank(orderKey)) {
      throw new PaybackException(DOMAIN_ENTITY_001, "orderKey가 없습니다");
    }
    if (packNo <= 0L) {
      throw new PaybackException(DOMAIN_ENTITY_001, "packNo는 0보다 커야 합니다");
    }
    if (orEmpty(orderUnitKeys).isEmpty()) {
      throw new PaybackException(DOMAIN_ENTITY_001, "orderUnitKeys 정보가 없습니다");
    }
  }

  public Map<String, OrderUnitKey> findOrderUnitKeyMap() {
    return orderUnitKeys.stream()
        .collect(toUnmodifiableMap(OrderUnitKey::getOrderUnitKey, identity()));
  }
}
