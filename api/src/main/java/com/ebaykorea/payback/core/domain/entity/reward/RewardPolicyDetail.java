package com.ebaykorea.payback.core.domain.entity.reward;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class RewardPolicyDetail {
  private String key;
  //스마일카드 추가 적립 금액
  private BigDecimal smileCardAdditionalCashbackAmount;
  // key 별 리워드 적용 정책 목록
  private List<RewardPolicyGroup> policyGroups;
}
