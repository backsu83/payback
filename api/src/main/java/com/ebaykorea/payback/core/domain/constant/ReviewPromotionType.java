package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewPromotionType {
  Unknown(0),
  Normal(102401),
  Premium(102402);

  private int gmarketCode;

}
