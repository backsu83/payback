package com.ebaykorea.payback.core.domain.entity.reward;

import static com.ebaykorea.payback.util.PaybackCollections.toMapBy;
import static com.ebaykorea.payback.util.PaybackInstants.DATE_TIME_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate;
import static com.ebaykorea.payback.util.PaybackStrings.isBlank;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import lombok.Value;

@Value
public class RewardCashbackPolicies {
  Map<Long, List<RewardCashbackPolicy>> cashbackPolicyMap;
  Map<Long, List<RewardBackendCashbackPolicy>> backendCashbackPolicyMap;
  Map<Long, RewardT2T3SmileCardCashbackPolicy> smileCardCashbackPolicyMap;
  Map<Long, RewardSsgPointPolicy> ssgPointPolicyMap;

  String useEnableDate;
  /**
   * 스마일카드(T0) 사용할 경우 적립예정금액
   */
  BigDecimal smileCardCashbackAmount;
  /**
   * 뉴스마일카드(T1,T2,T3) 사용할 경우 적립예정금액
   */
  BigDecimal newSmileCardCashbackAmount;

  public static RewardCashbackPolicies EMPTY = RewardCashbackPolicies.of(emptyList(), emptyList(), emptyList(), emptyList(), null, BigDecimal.ZERO, BigDecimal.ZERO);

  public static RewardCashbackPolicies of(
      final List<RewardCashbackPolicy> cashbackPolicies,
      final List<RewardBackendCashbackPolicy> backendCashbackPolicies,
      final List<RewardT2T3SmileCardCashbackPolicy> smileCardCashbackPolicies,
      final List<RewardSsgPointPolicy> ssgPointPolicies,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    return new RewardCashbackPolicies(
        cashbackPolicies.stream().collect(groupingBy(RewardCashbackPolicy::getPolicyKey)),
        backendCashbackPolicies.stream().collect(groupingBy(RewardBackendCashbackPolicy::getPolicyKey)),
        smileCardCashbackPolicies.stream().collect(toMapBy(RewardT2T3SmileCardCashbackPolicy::getPolicyKey)),
        ssgPointPolicies.stream().collect(toMapBy(RewardSsgPointPolicy::getPolicyKey)),
        useEnableDate,
        smileCardCashbackAmount,
        newSmileCardCashbackAmount);
  }

  private RewardCashbackPolicies(
      final Map<Long, List<RewardCashbackPolicy>> cashbackPolicyMap,
      final Map<Long, List<RewardBackendCashbackPolicy>> backendCashbackPolicyMap,
      final Map<Long, RewardT2T3SmileCardCashbackPolicy> smileCardCashbackPolicyMap,
      final Map<Long, RewardSsgPointPolicy> ssgPointPolicyMap,
      final String useEnableDate,
      final BigDecimal smileCardCashbackAmount,
      final BigDecimal newSmileCardCashbackAmount) {
    this.cashbackPolicyMap = cashbackPolicyMap;
    this.backendCashbackPolicyMap = backendCashbackPolicyMap;
    this.smileCardCashbackPolicyMap = smileCardCashbackPolicyMap;
    this.ssgPointPolicyMap = ssgPointPolicyMap;
    this.useEnableDate = useEnableDate;
    this.smileCardCashbackAmount = smileCardCashbackAmount;
    this.newSmileCardCashbackAmount = newSmileCardCashbackAmount;

    validate();
  }

  public void validate() {

  }

  public Map<CashbackType, List<RewardCashbackPolicy>> findRewardCashbackPolicyMapByCashbackType(final long policyKey) {
    return findRewardCashbackPolicies(policyKey).stream()
        .collect(groupingBy(RewardCashbackPolicy::getCashbackCd));
  }

  public Instant toUseEnableDate(final Instant orderDate) {
    if (isBlank(useEnableDate)) {
      return getDefaultEnableDate(orderDate);
    } else {
      return DATE_TIME_FORMATTER.parse(useEnableDate, Instant::from);
    }
  }

  private List<RewardCashbackPolicy> findRewardCashbackPolicies(final long policyKey) {
    return Optional.ofNullable(cashbackPolicyMap.get(policyKey))
        .orElse(emptyList());
  }

  private List<RewardBackendCashbackPolicy> findRewardBackendCashbackPolicies(final long policyKey) {
    return Optional.ofNullable(backendCashbackPolicyMap.get(policyKey))
        .orElse(emptyList());
  }

  public Map<Long, List<RewardBackendCashbackPolicy>> findRewardBackendCashbackPolicyMap(final long policyKey) {
    return findRewardBackendCashbackPolicies(policyKey).stream()
        .collect(groupingBy(RewardBackendCashbackPolicy::getCashbackSeq));
  }
}
