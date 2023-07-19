package com.ebaykorea.payback.consumer.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.List;

@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderCanceledAuctionEvent.class),
    @JsonSubTypes.Type(value = OrderCanceledGmarketEvent.class)
})
public interface OrderCanceledEvent {
  Long getPayNo();

  List<Long> getOrderNos();
}
