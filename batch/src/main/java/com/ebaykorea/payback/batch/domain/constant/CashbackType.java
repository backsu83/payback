package com.ebaykorea.payback.batch.domain.constant;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CashbackType {
  Unknown(null, ""),
  Item(1, "I"),
  SmilePay(2, "P"),
  Seller(3, "S"),
  Cart(4, "4"),
  ChargePay(5, "A"),
  ClubDay(6, "D");

  private final Integer code;
  private final String dbCode;
}
