package com.ebaykorea.payback.consumer.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderCanceledAuctionEvent {

  @JsonProperty("OrderNo")
  private Long orderNo;

  @JsonProperty("PackNo")
  private Long packNo;
}
