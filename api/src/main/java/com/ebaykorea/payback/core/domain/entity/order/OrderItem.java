package com.ebaykorea.payback.core.domain.entity.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class OrderItem {
  /** 상품 snapshot key */
  String itemSnapshotKey;

  /** 상품 번호 */
  String itemNo;

  /** 상품 판매 가격 */
  BigDecimal basePrice;

  /** 주문수량, 참고로 주문옵션(orderOptions) 도 이 수량을 따라간다. */
  int quantity;

  /** 주문 옵션 */
  List<OrderItemOption> options;

  /** 추가 구성 */
  List<OrderItemAddition> additions;

  /** 지점 추가 금액 */
  BigDecimal branchPrice;
}
