package com.ebaykorea.payback.core.domain.entity.order;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.Map;
import java.util.function.Function;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BundleDiscount {
  /** 복수 구매 할인 Snapshot 키 */
  String snapshotKey;

  /** orderUnit 별로 적용된 bundle Discount 상세 */
  List<BundleDiscountUnit> bundleDiscountUnits;
}
