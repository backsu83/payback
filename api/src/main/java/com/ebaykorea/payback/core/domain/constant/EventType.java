package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", "", ""),
  Event("Event", "RM01Y", "G8"),
  Toss("Toss", "RM02Y", "G9")
  ;

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;
}
