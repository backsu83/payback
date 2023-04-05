package com.ebaykorea.payback.consumer.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaybackSsgPointCancelResponse {
  private int code;
  private String message;
}
