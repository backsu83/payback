package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", 0),
  Toss("G9", 8166);

  private final String cashBalanceCode;
  private final int eventNo;
}
