package com.ebaykorea.payback.core.factory.cashback.impl;

import com.ebaykorea.payback.core.domain.constant.CashbackPayType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.SmilePayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.SmilePayCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;

@Component
public class SmilePayCashbackCreator {

  public CashbackUnit create(
      final Instant useEnableDate,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal cashbackAmount,
      final BigDecimal basisAmount,
      final List<RewardCashbackPolicy> rewardCashbackPolicies
  ) {
    return new SmilePayCashback(
        itemSnapshot.getItemNo(),
        itemSnapshot.toShopType(),
        cashbackAmount,
        basisAmount,
        useEnableDate,
        payment.isSmilePayPayment(),
        createCashbackPolicies(rewardCashbackPolicies),
        rewardCashbackPolicies.stream().map(RewardCashbackPolicy::getClubCashbackAmount).collect(summarizing()),
        rewardCashbackPolicies.stream().map(RewardCashbackPolicy::getPayCashbackAmount).collect(summarizing())
    );
  }

  private List<CashbackPolicy> createCashbackPolicies(final List<RewardCashbackPolicy> rewardCashbackPolicies) {
    return rewardCashbackPolicies.stream()
        .map(policy -> new SmilePayCashbackPolicy(
            policy.getCashbackSeq(),
            policy.getCashbackTitle(),
            CashbackPayType.FixRate.getCode(),
            policy.getPayType(),
            policy.getPayRate(),
            policy.getPayMaxMoney()
        ))
        .collect(Collectors.toUnmodifiableList());
  }

}
