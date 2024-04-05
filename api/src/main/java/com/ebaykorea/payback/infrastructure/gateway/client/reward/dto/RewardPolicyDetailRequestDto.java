package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardPolicyDetailRequestDto {
  //요청 키
  private String key;
  private String itemNo;
  private String largeCategoryCode;
  private String mediumCategoryCode;
  private String smallCategoryCode;
  private String sellerKey;
  private String itemType;
  private int qty;
  private BigDecimal price;
  //환금성 상품 여부
  private boolean redeemable;
}
