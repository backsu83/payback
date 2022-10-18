package com.ebaykorea.payback.core.domain.entity.order;

import com.ebaykorea.payback.core.domain.entity.Buyer;
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
  private Buyer buyer;

  /** 주문 일자 */
  private Instant orderDate;

  /** 주문 단위 정보 */
  private List<OrderUnit> orderUnits;

  /** 주문 복수 할인 정보 */
  private List<BundleDiscount> bundleDiscounts;

  /** 사이트 구분 테넌트 정보 (gmarket, gmarket-global, g9)*/
  private String tenant;
}
