package com.ebaykorea.payback.core.factory;

import com.ebaykorea.payback.core.domain.entity.cashback.*;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.*;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import java.util.Collections;
import java.util.List;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.OrderSiteType.Gmarket;

@Service
@RequiredArgsConstructor
public class PayCashbackFactory {
  private final CashbackFactory cashbackFactory;

  public PayCashback createPayCashback(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final Payment payment,
      final ItemSnapshots itemSnapshots,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return PayCashback.of(
        order.getOrderKey(),
        keyMap.getPackNo(),
        Gmarket, //TODO: repository에서 고정값으로 넣어주어도 될듯
        order.getOrderDate(),
        member,
        cashbackFactory.createCashbacks(keyMap, order, member, payment, itemSnapshots, rewardCashbackPolicies),
        createCashbackPolicies(rewardCashbackPolicies)
    );
  }

  List<CashbackPolicy> createCashbackPolicies(final RewardCashbackPolicies rewardCashbackPolicies) {
    //TODO
    return Collections.emptyList();
  }
}
