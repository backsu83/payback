package com.ebaykorea.payback.core.factory.impl;

import com.ebaykorea.payback.core.domain.constant.CashbackPayType;
import com.ebaykorea.payback.core.domain.entity.cashback.member.Member;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.CashbackUnit;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.ClubDayCashback;
import com.ebaykorea.payback.core.domain.entity.cashback.unit.policy.ClubDayCashbackPolicy;
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

import static com.ebaykorea.payback.core.domain.constant.CashbackType.ClubDay;
import static com.ebaykorea.payback.util.PaybackDecimals.orZero;
import static java.math.BigDecimal.ZERO;

@Component
public class ClubDayCashbackCreator {

  public CashbackUnit create(
      final Instant useEnableDate,
      final Member member,
      final Payment payment,
      final ItemSnapshot itemSnapshot,
      final BigDecimal cashbackAmount,
      final BigDecimal basisAmount,
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final Map<Long, List<RewardBackendCashbackPolicy>> rewardBackendPolicyMap
  ) {
    return new ClubDayCashback(
        itemSnapshot.getItemNo(),
        itemSnapshot.toShopType(),
        cashbackAmount,
        basisAmount,
        useEnableDate,
        payment.isSmilePayPayment(),
        member.isSmileClubMember(),
        createCashbackPolicy(rewardCashbackPolicies, rewardBackendPolicyMap)
    );
  }

  private List<CashbackPolicy> createCashbackPolicy(
      final List<RewardCashbackPolicy> rewardCashbackPolicies,
      final Map<Long, List<RewardBackendCashbackPolicy>> rewardBackendPolicyMap) {
    return rewardCashbackPolicies.stream()
        .map(policy -> {
          final var maybeBackendPolicy = rewardBackendPolicyMap.get(policy.getCashbackSeq()).stream()
              .filter(backendPolicy -> backendPolicy.getCashbackCode() == ClubDay)
              .findAny();

          return new ClubDayCashbackPolicy(
              policy.getCashbackSeq(),
              policy.getCashbackTitle(),
              CashbackPayType.FixRate.getCode(),
              policy.getPayType(),
              policy.getPayRate(),
              policy.getPayMaxMoney(),
              orZero(BigDecimal.valueOf(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getClubDaySaveMaxMoney).orElse(0), 0)),
              orZero(maybeBackendPolicy.map(RewardBackendCashbackPolicy::getClubDayPayRate).orElse(ZERO))
          );
        })
        .collect(Collectors.toUnmodifiableList());
  }
}
