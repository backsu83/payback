package com.ebaykorea.payback.core.domain.entity.order;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ItemSnapshot {
  /** 상품 스냅샷 키 */
  private String snapshotKey;

  /** 상품번호 */
  private String itemNo;

  /** 판매자고객번호 */
  private String sellerCustNo;

  /** 상품대분류코드 */
  private String itemLargeCategoryCode;

  /** 상품중분류코드 */
  private String itemMediumCategoryCode;

  /** 상품소분류코드 */
  private String itemSmallCategoryCode;

  /** 환금성 상품 여부 */
  private boolean isMoneyCategory;

  /** 스마일배송 상품 여부 */
  private boolean isSmileDelivery;

  /** 스마일프레시 상품 여부 */
  private boolean isSmileFresh;

  /** 구매자마일리지율 */
  private BigDecimal buyerMileageRate;
}
