package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
  Optional<Order> findOrder(String orderKey, String userKey);
  List<ItemSnapshot> findItemSnapshot(List<String> itemSnapshotKey);
}
