package com.ebaykorea.payback.core.domain.entity.order;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ExtraDiscountUnit {
  /** 즉시 할인 적용된 주문 단위 키 */
  String orderUnitKey;

  /** 즉시 할인 금액 */
  BigDecimal discountAmount;
}
