package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RewardPolicyRequestDto {
  //총 결제 금액
  private BigDecimal totalPrice;
  //클럽 회원 여부
  private boolean club;
  private List<RewardPolicyDetailRequestDto> details;
}
