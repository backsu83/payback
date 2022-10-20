package com.ebaykorea.payback.core.domain.entity.order;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Order {
  /** 주문 키 */
  private String orderKey;

  /** 결제 번호 */
  private Long paySeq;

  /** 구매자 정보 */
  private OrderBuyer buyer;

  /** 주문 일자 */
  private Instant orderDate;

  /** 주문 단위 정보 */
  private List<OrderUnit> orderUnits;

  /** 주문 복수 할인 정보 */
  private List<BundleDiscount> bundleDiscounts;

  //orderUnit별 복수할인 적용 금액
  public Map<String, BigDecimal> getBundleDiscountMap() {
    return orEmptyStream(bundleDiscounts)
        .map(BundleDiscount::getBundleDiscountUnits)
        .flatMap(Collection::stream)
        .collect(groupingBy(
            BundleDiscountUnit::getOrderUnitKey,
            mapping(BundleDiscountUnit::getDiscountAmount, summarizing())));
  }
}
