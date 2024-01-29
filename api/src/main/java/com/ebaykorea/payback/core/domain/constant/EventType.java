package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import java.util.Arrays;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
  Unknown("", "",""),
  DailyCheckIn("DailyCheckIn", "RM03Y", "G8"),
  Toss("Toss", "RM02Y","G9"),
  Review("Review", "RM04Y","GN"),
  ReviewPremium("ReviewPremium", "RM05Y","GP"),
  PurchaseAssociated("PurchaseAssociated", "RM06Y", "G4"),
  PurchaseDisassociated("PurchaseDisassociated", "RM07Y", "G4");

  private final String name;
  private final String auctionCode;
  private final String gmarketCode;

  private static final transient Map<String, EventType> mapAuctionCode = PaybackEnums.reverseMap(EventType.class, EventType::getAuctionCode);

  public static EventType auctionCodeOf(final String code) {return mapAuctionCode.getOrDefault(code, Unknown);

  }
}
