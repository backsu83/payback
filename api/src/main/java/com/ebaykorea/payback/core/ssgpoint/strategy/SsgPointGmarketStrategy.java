package com.ebaykorea.payback.core.ssgpoint.strategy;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointGmarketStrategy implements SsgPointStrategy {

  @Override
  public OrderSiteType getSiteType() {
    return OrderSiteType.Gmarket;
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
