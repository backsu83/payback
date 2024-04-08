package com.ebaykorea.payback.core.domain.entity.reward;

import com.ebaykorea.payback.core.domain.constant.CashbackType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RewardPolicyGroup {
  private long policyNo;
  private CashbackType type;
  private String title;
  // 적립 타입", "P"
  private String payType;
  // 서브 타입
  private String subType;
  // 적용 적립 금액
  private BigDecimal appliedSaveAmount;
  // 일반 회원 적립 금액
  private BigDecimal nonClubSaveAmount;
  // 클럽 회원 적립 금액
  private BigDecimal clubSaveAmount;
  // 적용 적립율
  private BigDecimal appliedSaveRate;
  // 일반 회원 적립율
  private BigDecimal nonClubSaveRate;
  // 클럽 회원 적립율
  private BigDecimal clubSaveRate;
  // 일반 회원 최대 적립 금액
  private BigDecimal nonClubMaxSaveAmount;
  // 클럽 최대 적립 금액
  private BigDecimal clubMaxSaveAmount;
}
