package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class RewardPolicyDto {
  private List<RewardPolicyDetailDto> policyDetails;
  // T0 스마일카드 캐시백 적립금액
  private BigDecimal t0SmileCardCashbackAmount;
  // T1,T2 스마일카드 캐시백 적립금액
  private BigDecimal t1t2SmileCardCashbackAmount;
  // T3 스마일카드 캐시백 적립금액
  private BigDecimal t3SmileCardCashbackAmount;
}
