package com.ebaykorea.payback.core.domain.entity.reward;

import static com.ebaykorea.payback.util.PaybackCollections.toMapBy;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static com.ebaykorea.payback.util.PaybackInstants.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import lombok.Value;

@Value
public class RewardCashbackPolicies {
  Map<Long, List<RewardCashbackPolicy>> cashbackPolicyMap;
  Map<Long, RewardBackendCashbackPolicy> backendCashbackPolicyMap;

  String useEnableDate;
  BigDecimal smileCardCashbackAmount;
  BigDecimal newSmileCardCashbackAmount;

  public static RewardCashbackPolicies of(
      final List<RewardCashbackPolicy> cashbackPolicies,
      final List<RewardBackendCashbackPolicy> backendCashbackPolicies,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    return new RewardCashbackPolicies(
        cashbackPolicies.stream().collect(groupingBy(RewardCashbackPolicy::getPolicyKey)),
        backendCashbackPolicies.stream().collect(toMapBy(RewardBackendCashbackPolicy::getPolicyKey)),
        useEnableDate,
        smileCardCashbackAmount,
        newSmileCardCashbackAmount);
  }

  private RewardCashbackPolicies(
      final Map<Long, List<RewardCashbackPolicy>> cashbackPolicyMap,
      final Map<Long, RewardBackendCashbackPolicy> backendCashbackPolicyMap,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    this.cashbackPolicyMap = cashbackPolicyMap;
    this.backendCashbackPolicyMap = backendCashbackPolicyMap;
    this.useEnableDate = useEnableDate;
    this.smileCardCashbackAmount = smileCardCashbackAmount;
    this.newSmileCardCashbackAmount = newSmileCardCashbackAmount;

    validate();
  }

  public void validate() {

  }

  //정책이 여러개 등록될 경우를 고려하여 key 및 타입별 캐시백 금액을 Sum한 형태로 가져옵니다
  //TODO: https://jira.ebaykorea.com/browse/RWD-971 확인 필요
  public BigDecimal getCashbackAmount(final long policyKey, final CashbackType cashbackType) {
    return findRewardCashbackPolicies(policyKey).stream()
        .filter(p -> p.getCashbackCd() == cashbackType)
        .map(RewardCashbackPolicy::getCashbackAmount)
        .map(BigDecimal::valueOf)
        .collect(summarizing());
  }

  public Instant toUseEnableDate(final Instant orderDate) {
    if (isBlank(useEnableDate)) {
      return getDefaultEnableDate(orderDate);
    } else {
      return DATE_TIME_FORMATTER.parse(useEnableDate, Instant::from);
    }
  }

  public List<RewardCashbackPolicy> findRewardCashbackPolicies(final long policyKey) {
    return Optional.ofNullable(cashbackPolicyMap.get(policyKey))
        .orElse(Collections.emptyList());
  }

  public Optional<RewardBackendCashbackPolicy> findBackendRewardCashbackPolicy(final long policyKey) {
    return Optional.ofNullable(backendCashbackPolicyMap.get(policyKey));
  }
}
