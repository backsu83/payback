package com.ebaykorea.payback.core.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointAuth;
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
        .statusType(PointStatusType.Ready)
        .tradeType(PointTradeType.Save)
        .build();
  }

  @Override
  public SsgPointStatus cancel() {
    return SsgPointStatus.builder()
        .statusType(PointStatusType.Ready)
        .tradeType(PointTradeType.Cancel)
        .build();
  }
}
