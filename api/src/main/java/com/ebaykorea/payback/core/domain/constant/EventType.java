package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", ""),
  Event("Event", "RM01Y"),
  Toss("Toss", "RM02Y")
  ;

  private final String name;
  private final String auctionCode;
}
