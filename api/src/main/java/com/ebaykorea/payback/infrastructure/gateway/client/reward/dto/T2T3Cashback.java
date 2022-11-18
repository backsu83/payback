package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.SmileCardType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class T2T3Cashback {
  // "G9" or "Gmarket"
  private OrderSiteType siteCode;
  // pack no
  private long packNo;
  // 사자 주문번호
  private long buyOrderNo;
  // 스마일카드 타입 : "" or "T0" or "T1" or "T2" or "T3"
  private SmileCardType smileCardType;
  // cashback basis money
  private BigDecimal itemPrice;
  // 해당 주문번호의 IfSmileCardT2T3CashbackAmount
  private BigDecimal t2T3CashbackAmnt;
  //스마일배송: SD, 스마일프레시: SF
  private String itemType;
}
