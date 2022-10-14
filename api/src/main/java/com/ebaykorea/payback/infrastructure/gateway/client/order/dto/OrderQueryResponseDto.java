package com.ebaykorea.payback.infrastructure.gateway.client.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderQueryResponseDto {
  /** 주문 키 */
  private String orderKey;

  /** 거래 키 */
  private String txKey;

  /** 결제 번호 */
  private String paySeq;

  /** 주문 기본 정보 */
  private OrderBaseDto orderBase;

  /** 구매자 정보 */
  private BuyerDto buyer;

  /** 주문 단위 정보 */
  private List<OrderUnitDto> orderUnits;

  /** 주문 복수 할인 정보 */
  private List<BundleDiscountDto> bundleDiscounts;

  /** 사이트 구분 테넌트 정보 (gmarket, gmarket-global, g9)*/
  private String tenant;
}
