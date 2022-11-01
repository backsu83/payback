package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshots;
import com.ebaykorea.payback.core.domain.entity.order.Order;

import java.util.List;

public interface OrderGateway {
  Order getOrder(String orderKey);
  ItemSnapshots getItemSnapshot(List<String> itemSnapshotKey);
}
