package com.ebaykorea.payback.core.ssgpoint.fatory;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_002;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.ssgpoint.strategy.SsgPointStrategy;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SsgPointFactory {

  private Set<SsgPointStrategy> ssgPointStrategySet;

  public SsgPointFactory(Set<SsgPointStrategy> ssgPointStrategySet) {
    this.ssgPointStrategySet = ssgPointStrategySet;
  }

  public SsgPointStrategy find(OrderSiteType siteType) {
    return ssgPointStrategySet.stream()
        .filter(ssgpoint -> ssgpoint.getSiteType() == siteType)
        .findAny()
        .orElseThrow(() -> new PaybackException(DOMAIN_ENTITY_002 , "ssgpoint sitetype"));
  }
}
