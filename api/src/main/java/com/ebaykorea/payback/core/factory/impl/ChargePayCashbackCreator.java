package com.ebaykorea.payback.core.factory.impl;

import com.ebaykorea.payback.core.domain.constant.CashbackPayType;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.ChargePayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.ChargePayCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.CashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.order.ItemSnapshot;
import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.domain.entity.reward.RewardBackendCashbackPolicy;
import com.ebaykorea.payback.core.domain.entity.reward.RewardCashbackPolicy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.domain.constant.CashbackType.ChargePay;
import static com.ebaykorea.payback.util.PaybackDecimals.orZero;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static java.math.BigDecimal.ZERO;

@Component
public class ChargePayCashbackCreator {

  public CashbackUnit create(
      final Instant useEnableDate,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal cashbackAmount,
      final BigDecimal basisAmount,
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final Map<Long, List<RewardBackendCashbackPolicy>> rewardBackendPolicyMap) {
    return new ChargePayCashback(
        itemSnapshot.getItemNo(),
        itemSnapshot.toShopType(),
        cashbackAmount,
        basisAmount,
        useEnableDate,
        rewardCashbackPolicies.stream().map(RewardCashbackPolicy::getAutoChargeClubAmount).collect(summarizing()),
        rewardCashbackPolicies.stream().map(RewardCashbackPolicy::getAutoChargeAmount).collect(summarizing()),
        payment.isChargePayment(),
        createCashbackPolicies(rewardCashbackPolicies, rewardBackendPolicyMap)
    );
  }

  private List<CashbackPolicy> createCashbackPolicies(
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final Map<Long, List<RewardBackendCashbackPolicy>> rewardBackendPolicyMap) {
    return rewardCashbackPolicies.stream()
        .map(policy -> {
          final var maybeBackendPolicy = rewardBackendPolicyMap.get(policy.getCashbackSeq()).stream()
              .filter(backendPolicy -> backendPolicy.getCashbackCode() == ChargePay)
              .findAny();

          return new ChargePayCashbackPolicy(
              policy.getCashbackSeq(),
              policy.getCashbackTitle(),
              CashbackPayType.FixRate.getCode(),
              policy.getPayType(),
              policy.getPayRate(),
              policy.getPayMaxMoney(),
              orZero(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getChargePayRewardRate).orElse(ZERO)),
              orZero(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getChargePayRewardClubRate).orElse(ZERO)),
              orZero(BigDecimal.valueOf(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getChargePayRewardMaxMoney).orElse(0))),
              orZero(BigDecimal.valueOf(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getChargePayRewardClubMaxMoney).orElse(0)))
          );
        })
        .collect(Collectors.toUnmodifiableList());
  }
}
