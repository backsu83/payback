package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", "", ""),
  DailyCheckIn("DailyCheckIn", "RM01Y", "G8"),
  Toss("Toss", "RM02Y", "G9")
  ;

  //TODO 액션코드 (=캐시코드 ) 변경 예정.
  Unknown("", "",""),
  Event("Event", "RM01Y","A003"),
  Toss("Toss", "RM02Y","A003"),
  Review("Review", "RM03Y","A004"),
  ReviewPremium("Premium", "RM04Y","A005");

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;
}
