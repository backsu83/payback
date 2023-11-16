package com.ebaykorea.payback.scheduler.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown(""),
  Toss("Toss");

  private final String name;
}
