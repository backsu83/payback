package com.ebaykorea.payback.core.domain.entity.reward;

import static com.ebaykorea.payback.util.PaybackDateTimes.LOCAL_DATE_FORMATTER;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
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
    return new RewardCashbackPolicies(
        cashbackPolicies,
        backendCashbackPolicies,
        useEnableDate,
        smileCardCashbackAmount,
        newSmileCardCashbackAmount);
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

  public Map<Long, List<RewardCashbackPolicy>> findCashbackPolicyMap() {
    return cashbackPolicies.stream()
        .collect(groupingBy(RewardCashbackPolicy::getPolicyKey));
  }

  public Map<Long, List<RewardBackendCashbackPolicy>> findBackendCashbackPolicyMep() {
    return backendCashbackPolicies.stream().collect(groupingBy(RewardBackendCashbackPolicy::getPolicyKey));
  }

  //정책이 여러개 등록될 경우를 고려하여 key 및 타입별 캐시백 금액을 Sum한 형태로 가져옵니다
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
      return LOCAL_DATE_FORMATTER.parse(useEnableDate, Instant::from);
    }
  }

  private List<RewardCashbackPolicy> findRewardCashbackPolicies(final long policyKey) {
    return Optional.ofNullable(findCashbackPolicyMap().get(policyKey))
        .orElse(Collections.emptyList());
  }
}
