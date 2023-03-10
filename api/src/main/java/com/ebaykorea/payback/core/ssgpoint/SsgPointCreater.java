package com.ebaykorea.payback.core.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointCreater {
  private final SsgPointUnitCreater ssgPointCreater;

  public SsgPoint create(
      final List<RewardSsgPointPolicy> ssgPointPolicies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointCard cardNo
  ) {
    return SsgPoint.of(
        keyMap.getPackNo(),
        order.getBuyer().getBuyerId(),
        order.getOrderDate(),
        OrderSiteType.Gmarket,
        ssgPointCreater.create(ssgPointPolicies, order, keyMap, cardNo)
    );
  }
}
