package com.ebaykorea.payback.consumer.event;

import java.util.List;

public interface OrderCanceledEvent {
  Long getPayNo();
  List<Long> getOrderNos();
}
