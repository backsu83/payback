package com.ebaykorea.payback.core.domain.entity.ssgpoint.state;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointAuth;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;
import org.springframework.stereotype.Component;

@Component
public class SsgPointGmarketState implements SsgPointState {

  private final static String CardType = "S001";

  @Override
  public OrderSiteType site() {
    return OrderSiteType.Gmarket;
  }

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
