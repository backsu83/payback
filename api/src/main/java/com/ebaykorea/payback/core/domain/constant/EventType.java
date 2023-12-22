package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

  //TODO auctionCode, gmarketCode 코드 변경 예정 (=캐시코드 ) .
  Unknown("", "","" , 0),
  DailyCheckIn("DailyCheckIn", "RM03Y", "G8",90),
  Toss("Toss", "RM02Y","G9", 30),
  Review("Review", "RM04Y","GN", 180),
  ReviewPremium("ReviewPremium", "RM05Y","GP", 180);

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;
  private final Integer period;
}
