package com.ebaykorea.payback.core.ssgpoint.state;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_003;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.exception.PaybackException;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SsgPointStateDelegate {

  private Set<SsgPointState> ssgPointStrategySet;

  public SsgPointStateDelegate(Set<SsgPointState> ssgPointStrategySet) {
    this.ssgPointStrategySet = ssgPointStrategySet;
  }

  public SsgPointState find(OrderSiteType siteType) {
    return ssgPointStrategySet.stream()
        .filter(entry -> entry.site() == siteType)
        .findAny()
        .orElseThrow(() -> new PaybackException(DOMAIN_SSG_ENTITY_003));
  }
}
