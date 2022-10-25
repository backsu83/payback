package com.ebaykorea.payback.core.domain.entity.reward;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
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
      final List<RewardCashbackPolicy> cashbackPolicies,
      final List<RewardBackendCashbackPolicy> backendCashbackPolicies,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    return new RewardCashbackPolicies(cashbackPolicies, backendCashbackPolicies, useEnableDate, smileCardCashbackAmount, newSmileCardCashbackAmount);
  }

  private RewardCashbackPolicies(
      final List<RewardCashbackPolicy> cashbackPolicies,
      final List<RewardBackendCashbackPolicy> backendCashbackPolicies,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    this.cashbackPolicies = cashbackPolicies;
    this.backendCashbackPolicies = backendCashbackPolicies;
    this.useEnableDate = useEnableDate;
    this.smileCardCashbackAmount = smileCardCashbackAmount;
    this.newSmileCardCashbackAmount = newSmileCardCashbackAmount;

    validate();
  }

  public void validate() {

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
