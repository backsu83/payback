package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SsgPointCreater {
  private final SsgPointUnitCreater ssgPointUnitCreater;

  public SsgPoint withReadyUnits(
      final RewardCashbackPolicies ssgPointPolicies,
      final Member member,
      final Order order,
      final KeyMap keyMap,
      final Payment payment,
      final SsgPointState ssgPointState,
      final OrderSiteType orderSiteType
      ) {
    return SsgPoint.of(
        keyMap.getPackNo(),
        member.getBuyerNo(),
        member.isSsgMember(),
        order.getOrderDate(),
        orderSiteType,
        ssgPointUnitCreater.readyUnits(ssgPointPolicies.getSsgPointPolicyMap(), order, keyMap, payment, ssgPointState)
    );
  }

  public SsgPoint withCancelUnit(final SsgPointTarget ssgPointTarget,
      final SsgPointState ssgPointState,
      final String adminId) {
    return SsgPoint.of(
        ssgPointTarget.getPackNo(),
        ssgPointTarget.getBuyerId(),
        true,
        ssgPointTarget.getOrderDate(),
        ssgPointTarget.getSiteType(),
        List.of(ssgPointUnitCreater.cancelUnit(ssgPointTarget, ssgPointState, adminId)));
  }

  public SsgPoint withCancelBeforeSaveUnit(final SsgPointTarget ssgPointTarget,
      final SsgPointState ssgPointState,
      final String adminId) {
    return SsgPoint.of(
        ssgPointTarget.getPackNo(),
        ssgPointTarget.getBuyerId(),
        true,
        ssgPointTarget.getOrderDate(),
        ssgPointTarget.getSiteType(),
        List.of(ssgPointUnitCreater.cancelBeforeSaveUnit(ssgPointTarget, ssgPointState, adminId)));
  }
}
