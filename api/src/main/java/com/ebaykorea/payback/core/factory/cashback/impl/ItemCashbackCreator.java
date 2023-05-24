package com.ebaykorea.payback.core.factory.cashback.impl;

import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.ItemCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.ItemCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemCashbackCreator {

  public CashbackUnit createItemCashback(
      Instant useEnableDate,
      Payment payment,
      ItemSnapshot itemSnapshot,
      BigDecimal cashbackAmount,
      BigDecimal basisAmount,
      List<RewardCashbackPolicy> rewardCashbackPolicies
  ) {
    return new ItemCashback(
        itemSnapshot.getItemNo(),
        itemSnapshot.toShopType(),
        cashbackAmount,
        basisAmount,
        useEnableDate,
        payment.isSmilePayPayment(),
        createCashbackPolicies(rewardCashbackPolicies)
    );
  }

  private List<CashbackPolicy> createCashbackPolicies(final List<RewardCashbackPolicy> rewardCashbackPolicies) {
    return rewardCashbackPolicies.stream()
        .map(policy -> new ItemCashbackPolicy(
            policy.getCashbackSeq(),
            policy.getCashbackTitle(),
            null,
            policy.getPayType(),
            policy.getPayRate(),
            policy.getPayMaxMoney()))
        .collect(Collectors.toUnmodifiableList());
  }
}
