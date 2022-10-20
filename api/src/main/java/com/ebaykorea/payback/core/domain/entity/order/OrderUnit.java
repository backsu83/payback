package com.ebaykorea.payback.core.domain.entity.order;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static com.ebaykorea.payback.util.PaybackObjects.orElse;

import java.math.BigDecimal;
import lombok.*;

import java.util.List;

@Value
@Builder
public class OrderUnit {

  /**
   * 주문 단위 Key
   */
  String orderUnitKey;

  /**
   * 주문 Item 정보
   */
  OrderItem orderItem;

  /**
   * 상품 할인 정보
   */
  List<OrderUnitDiscount> itemDiscounts;

  /**
   * 쿠폰 정보
   */
  List<OrderUnitCoupon> coupons;

  public BigDecimal orderUnitPrice() {
    return orderItem.orderItemPrice()
        .subtract(itemDiscountPrice())
        .subtract(couponPrice());
  }

  public BigDecimal orderUnitPriceWithBundleDiscount(BigDecimal bundleDiscountPrice) {
    return orderUnitPrice()
        .subtract(orElse(bundleDiscountPrice, BigDecimal.ZERO));
  }

  public BigDecimal itemDiscountPrice() {
    return orEmptyStream(itemDiscounts)
        .map(OrderUnitDiscount::getDiscountAmount)
        .collect(summarizing());
  }

  public BigDecimal couponPrice() {
    return orEmptyStream(coupons)
        .map(OrderUnitCoupon::getCouponAmount)
        .collect(summarizing());
  }
}
