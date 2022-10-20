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

  /** 상품타입 */
  private boolean isMoneyCategory;

  /** 구매자마일리지율 */
  private BigDecimal buyerMileageRate;
}
