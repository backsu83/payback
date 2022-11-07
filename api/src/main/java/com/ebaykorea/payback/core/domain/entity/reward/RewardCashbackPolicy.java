package com.ebaykorea.payback.core.domain.entity.reward;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RewardCashbackPolicy {
  //buyOrderNo
  long policyKey;
  /** 캐시백 코드 */
  CashbackType cashbackCd;
  /** 캐시백금액 */
  Integer cashbackAmount;
  /** 캐시백 정책번호 */
  Long cashbackSeq;
  /** 적립타입 P, W */
  String payType;
  /** 적립율 */
  BigDecimal payRate;
  /** 최대적립가능금액 */
  BigDecimal payMaxMoney;
  /** 캐시백타이틀 */
  String cashbackTitle;
}
