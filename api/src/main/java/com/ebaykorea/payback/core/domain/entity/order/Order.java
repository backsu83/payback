package com.ebaykorea.payback.core.domain.entity.order;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;
import static com.ebaykorea.payback.util.PaybackCollections.*;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static com.ebaykorea.payback.util.PaybackObjects.orElse;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import com.ebaykorea.payback.core.exception.PaybackException;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class Order {
  /**
   * 주문 키
   */
  String orderKey;

  /**
   * 결제 번호
   */
  Long paySeq;

  /**
   * 구매자 정보
   */
  OrderBuyer buyer;

  /**
   * 주문 일자
   */
  Instant orderDate;

  /**
   * 주문 단위 정보
   */
  List<OrderUnit> orderUnits;

  /**
   * 주문 복수 할인 정보
   */
  List<BundleDiscount> bundleDiscounts;

  public static Order of(
      final String orderKey,
      final Long paySeq,
      final OrderBuyer buyer,
      final Instant orderDate,
      final List<OrderUnit> orderUnits,
      final List<BundleDiscount> bundleDiscounts
  ) {
    return new Order(orderKey, paySeq, buyer, orderDate, orderUnits, bundleDiscounts);
  }

  private Order(
      final String orderKey,
      final Long paySeq,
      final OrderBuyer buyer,
      final Instant orderDate,
      final List<OrderUnit> orderUnits,
      final List<BundleDiscount> bundleDiscounts
  ) {
    this.orderKey = orderKey;
    this.paySeq = paySeq;
    this.buyer = buyer;
    this.orderDate = orderDate;
    this.orderUnits = orderUnits;
    this.bundleDiscounts = bundleDiscounts;

    validate();
  }

  // 불변식
  private void validate() {
    if (orElse(paySeq, 0L) == 0L) {
      throw new PaybackException(DOMAIN_ENTITY_001, "paySeq 값이 없습니다");
    }
    if (buyer == null) {
      throw new PaybackException(DOMAIN_ENTITY_001, "buyer는 null일 수 없습니다");
    }
    if (orderDate == null) {
      throw new PaybackException(DOMAIN_ENTITY_001, "orderDate는 null일 수 없습니다");
    }
    if (isEmpty(orderUnits)) {
      throw new PaybackException(DOMAIN_ENTITY_001, "orderUnits 정보가 없습니다");
    }
  }

  //orderUnit별 복수할인 적용 금액
  public Map<String, BigDecimal> findBundleDiscountMap() {
    return orEmptyStream(bundleDiscounts)
        .map(BundleDiscount::getBundleDiscountUnits)
        .flatMap(Collection::stream)
        .collect(groupingBy(
            BundleDiscountUnit::getOrderUnitKey,
            mapping(BundleDiscountUnit::getDiscountAmount, summarizing())));
  }

  public List<String> findItemSnapshotKeys() {
    return orderUnits.stream()
        .map(OrderUnit::getItemSnapshotKey)
        .collect(Collectors.toUnmodifiableList());
  }

  // 캐시백 적용 대상 주문 여부
  //TODO: 글로벌과 G9 여부도 여기서 체크해야할듯
  public boolean isForCashback() {
    return isMember();
  }

  private boolean isMember() {
    return buyer.isMember();
  }
}
