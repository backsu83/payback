package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

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

  private static final Map<String, CashbackType> map = PaybackEnums.reverseMap(CashbackType.class, CashbackType::getDbCode);

  @JsonCreator
  public static String toCashbackName(String dbCode) {
    return map.getOrDefault(dbCode, Unknown).toString();
  }

  @JsonValue
  public Integer toValue() {
    return this.code;
  }
}
