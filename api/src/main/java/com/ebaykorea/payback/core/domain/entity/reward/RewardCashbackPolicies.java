package com.ebaykorea.payback.core.domain.entity.reward;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Value;

@Value
public class RewardCashbackPolicies {
  List<RewardCashbackPolicy> cashbackPolicies;
  List<RewardBackendCashbackPolicy> backendCashbackPolicies;

  String useEnableDate;
  BigDecimal smileCardCashbackAmount;
  BigDecimal newSmileCardCashbackAmount;

  public static RewardCashbackPolicies of(
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final List<RewardBackendCashbackPolicy> rewardBackendCashbackPolicies,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    return new RewardCashbackPolicies(rewardCashbackPolicies, rewardBackendCashbackPolicies, useEnableDate, smileCardCashbackAmount, newSmileCardCashbackAmount);
  }

  public Map<Long, List<RewardCashbackPolicy>> policyMapByPolicyKey() {
    return cashbackPolicies.stream()
        .collect(groupingBy(RewardCashbackPolicy::getPolicyKey));
  }

  public Map<Long, List<RewardBackendCashbackPolicy>> backendPolicyMapByPolicyKey() {
    return backendCashbackPolicies.stream()
        .collect(groupingBy(RewardBackendCashbackPolicy::getPolicyKey));
  }
}
