package com.ebaykorea.payback.core.factory;

import com.ebaykorea.payback.core.domain.entity.cashback.*;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.order.*;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;

import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.OrderSiteType.Gmarket;

@Service
@RequiredArgsConstructor
public class PayCashbackCreator {
  private final CashbackCreator cashbackCreator;
  private final SmileCardCashbackCreator smileCardCashbackCreator;

  public PayCashback create(
      final KeyMap keyMap,
      final Order order,
      final Member member,
      final Payment payment,
      final ItemSnapshots itemSnapshots,
      final RewardCashbackPolicies rewardCashbackPolicies
  ) {
    return PayCashback.of(
        keyMap.getTxKey(),
        keyMap.getPackNo(),
        Gmarket, //TODO: repository에서 고정값으로 넣어주어도 될듯
        order.getOrderDate(),
        member,
        cashbackCreator.createCashbacks(keyMap, order, member, payment, itemSnapshots, rewardCashbackPolicies),
        smileCardCashbackCreator.createSmileCardCashback(keyMap, order, payment, itemSnapshots, rewardCashbackPolicies)
    );
  }
}
