package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointStatus;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointCreater {
  private final SsgPointUnitCreater ssgPointCreater;

  public SsgPoint create(
      final Map<Long, RewardSsgPointPolicy> ssgPointPolicies,
      final Order order,
      final KeyMap keyMap,
      final OrderSiteType orderSiteType,
      final SsgPointStatus ssgPointStatus
  ) {
    return SsgPoint.of(
        keyMap.getPackNo(),
        order.getBuyer().getBuyerId(),
        order.getOrderDate(),
        orderSiteType,
        ssgPointCreater.create(ssgPointPolicies, order, keyMap, ssgPointStatus)
    );
  }

}
