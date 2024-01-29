package com.ebaykorea.payback.scheduler.model.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionSmileCashEventType {
  Unknown("", ""),
  RM03Y("DailyCheckIn", "APR0003"),
  RM04Y("Review", "APR0001"),
  RM05Y("ReviewPremium", "APR0002");

  private final String name;
  private final String promotionId;
}
