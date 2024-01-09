package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", "",""),
  DailyCheckIn("DailyCheckIn", "RM03Y", "G8"),
  Toss("Toss", "RM02Y","G9"),
  Review("Review", "RM04Y","GN"),
  ReviewPremium("ReviewPremium", "RM05Y","GP");

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;
}
