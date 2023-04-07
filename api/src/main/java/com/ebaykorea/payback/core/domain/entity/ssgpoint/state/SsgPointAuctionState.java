package com.ebaykorea.payback.core.domain.entity.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;
import org.springframework.stereotype.Component;

@Component
public class SsgPointAuctionState implements SsgPointState {

  private final static String CardType = "S002";

  @Override
  public OrderSiteType site() { return OrderSiteType.Auction; }

  @Override
  public SsgPointStatus ready() {
    return SsgPointStatus.builder()
        .pointStatusType(PointStatusType.Ready)
        .pointTradeType(PointTradeType.Save)
        .smileClubCardType(CardType)
        .build();
  }

  @Override
  public SsgPointStatus cancel() {
    return SsgPointStatus.builder()
        .pointStatusType(PointStatusType.Ready)
        .pointTradeType(PointTradeType.Cancel)
        .smileClubCardType(CardType)
        .build();
  }
}
