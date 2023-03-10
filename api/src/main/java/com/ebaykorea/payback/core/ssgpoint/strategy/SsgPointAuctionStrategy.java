package com.ebaykorea.payback.core.ssgpoint.strategy;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.gateway.OrderGateway;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.core.gateway.RewardGateway;
import com.ebaykorea.payback.core.gateway.TransactionGateway;
import com.ebaykorea.payback.core.service.MemberService;
import com.ebaykorea.payback.core.ssgpoint.service.SsgPointGmarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointAuctionStrategy implements SsgPointStrategy {

  @Override
  public OrderSiteType getSiteType() {
    return OrderSiteType.Auction;
  }

  @Override
  public SsgPoint save(SsgPoint ssgPoint) {
    return null;
  }

  @Override
  public void cancel(SsgPoint ssgPoint) {
  }

  @Override
  public void use(SsgPoint ssgPoint) {

  }

}
