package com.ebaykorea.payback.infrastructure.gateway.client.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemSnapshotDto {
  /** 상품 스냅샷 키 */
  private String snapshotKey;

  /** 상품번호 */
  private String itemNo;

  /** 판매자고객번호 */
  String sellerCustNo;

  /** 상품대분류코드 */
  String itemLargeCategoryCode;

  /** 상품중분류코드 */
  String itemMediumCategoryCode;

  /** 상품소분류코드 */
  String itemSmallCategoryCode;

  /** 상품타입 */
  ItemTypeDto itemType;

  /** 구매자마일리지율 */
  BigDecimal buyerMileageRate;
}
