package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown(""),
  Normal("G3"),
  Toss("TODO"); //TODO

  private final String code;
}
