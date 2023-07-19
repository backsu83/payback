package com.ebaykorea.payback.consumer.event;

import lombok.Data;

import java.util.List;

@Data
public class OrderCanceledAuctionEvent implements OrderCanceledEvent{

  private Long orderNo;
  private Long payNo;

  @Override
  public List<Long> getOrderNos() {
    return List.of(orderNo);
  }
}
