package com.ebaykorea.payback.core.factory.cashback.impl;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.DefaultCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.DefaultCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCashbackCreator {

  public CashbackUnit create(
      CashbackType cashbackType,
      Instant useEnableDate,
      ItemSnapshot itemSnapshot,
      BigDecimal cashbackAmount,
      BigDecimal basisAmount,
      List<RewardCashbackPolicy> rewardCashbackPolicies) {
    return new DefaultCashback(
        itemSnapshot.getItemNo(),
        cashbackType,
        itemSnapshot.toShopType(),
        cashbackAmount,
        basisAmount,
        useEnableDate,
        createCashbackPolicies(rewardCashbackPolicies)
    );
  }

  private List<CashbackPolicy> createCashbackPolicies(final List<RewardCashbackPolicy> rewardCashbackPolicies) {
    return rewardCashbackPolicies.stream()
        .map(policy -> new DefaultCashbackPolicy(
            policy.getCashbackSeq(),
            policy.getCashbackCd(),
            policy.getCashbackTitle(),
            null,
            policy.getPayType(),
            policy.getPayRate(),
            policy.getPayMaxMoney()
        ))
        .collect(Collectors.toUnmodifiableList());
  }
}
