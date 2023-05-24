package com.ebaykorea.payback.consumer.event;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
  /** 거래 Key */
  @NotEmpty(message = "txKey is empty")
  String txKey;

  /** 주문 Key */
  @NotEmpty(message = "orderKey is empty")
  String orderKey;

}
