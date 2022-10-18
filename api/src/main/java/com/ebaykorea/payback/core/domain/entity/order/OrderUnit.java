package com.ebaykorea.payback.core.domain.entity.order;

import lombok.*;

import java.util.List;

@Value
@Builder
public class OrderUnit {
  /** 주문 단위 Key */
  String orderUnitKey;

  /** 주문 Item 정보 */
  OrderItem orderItem;

  /** 상품 할인 정보 */
  List<OrderUnitDiscount> itemDiscounts;

  /** 쿠폰 정보 */
  List<OrderUnitCoupon> coupons;
}
