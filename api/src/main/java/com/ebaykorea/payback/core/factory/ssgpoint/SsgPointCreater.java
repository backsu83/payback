package com.ebaykorea.payback.core.factory.ssgpoint;

import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.domain.entity.order.Order;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import com.ebaykorea.payback.core.domain.entity.reward.RewardSsgPointPolicy;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.ebaykorea.payback.core.dto.ssgpoint.CancelSsgPointRequestDto;
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
      final SsgPointState ssgPointState
      ) {
    return SsgPoint.of(
        keyMap.getPackNo(),
        member.getBuyerNo(),
        member.isSsgMember(),
        order.getOrderDate(),
        ssgPointState.site(),
        ssgPointUnitCreater.readyUnits(ssgPointPolicies.getSsgPointPolicyMap(), order, keyMap, payment, ssgPointState)
    );
  }

  public SsgPoint withCancelUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    return SsgPoint.of(
        ssgPointTarget.getPackNo(),
        ssgPointTarget.getBuyerId(),
        true,
        ssgPointTarget.getOrderDate(),
        request.getSiteType(),
        List.of(ssgPointUnitCreater.cancelUnit(request, ssgPointTarget)));
  }

  public SsgPoint withWithholdUnit(final CancelSsgPointRequestDto request, final SsgPointTarget ssgPointTarget) {
    return SsgPoint.of(
        ssgPointTarget.getPackNo(),
        ssgPointTarget.getBuyerId(),
        true,
        ssgPointTarget.getOrderDate(),
        request.getSiteType(),
        List.of(ssgPointUnitCreater.withholdUnit(request, ssgPointTarget)));
  }
}
