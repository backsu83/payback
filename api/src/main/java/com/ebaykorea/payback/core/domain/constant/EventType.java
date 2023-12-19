package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

  //TODO auctionCode, gmarketCode 코드 변경 예정 (=캐시코드 ) .
  Unknown("", "","",""),
  DailyCheckIn("DailyCheckIn", "RM01Y", "A002","G8"),
  Toss("Toss", "RM02Y","A003","G9"),
  Review("Review", "RM03Y","A004","G8"),
  ReviewPremium("ReviewPremium", "RM04Y","A005","G8");

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;
  private final String cashBalanceCode;
}
