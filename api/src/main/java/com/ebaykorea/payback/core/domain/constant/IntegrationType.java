package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationType {
  Unknown(""),
  Mass("L"), //대량 적립
  RealTime("Q");

  private final String code;
}
