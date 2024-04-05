package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class RewardPolicy {
  private List<RewardPolicyDetail> policyDetails;
  // T0 스마일카드 캐시백 적립금액
  private BigDecimal t0SmileCardCashbackAmount;
  // T1,T2 스마일카드 캐시백 적립금액
  private BigDecimal t1t2SmileCardCashbackAmount;
  // T3 스마일카드 캐시백 적립금액
  private BigDecimal t3SmileCardCashbackAmount;

  public static RewardPolicy EMPTY = RewardPolicy.of(Collections.emptyList(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
}
