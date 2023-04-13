package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SsgPointCreater {
  private final SsgPointUnitCreater ssgPointCreater;

  public SsgPoint create(
      final Map<Long, RewardSsgPointPolicy> ssgPointPolicies,
      final Order order,
      final KeyMap keyMap,
      final SsgPointState ssgPointState
      ) {
    return SsgPoint.of(
        keyMap.getPackNo(),
        order.getBuyer().getBuyerNo(),
        order.getOrderDate(),
        ssgPointState.site(),
        ssgPointCreater.create(ssgPointPolicies, order, keyMap, ssgPointState)
    );
  }

}
