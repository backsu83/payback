package com.ebaykorea.payback.consumer.event;

import lombok.Data;

@Data
public class OrderCanceledAuctionEvent {

  private Long orderNo;
  private Long payNo;
}
