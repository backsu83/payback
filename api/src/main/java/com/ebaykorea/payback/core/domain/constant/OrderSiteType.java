package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum OrderSiteType {
  Unknown(null, "" , ""),
  Gmarket(0, "G" , "GMK"),
  Auction(1, "A" , "AUC");

  private final Integer code;
  private final String shortCode;
  private final String ticker;

  private static transient Map<String, OrderSiteType> map = PaybackEnums.reverseMap(OrderSiteType.class, OrderSiteType::getShortCode);

  @JsonCreator
  public static OrderSiteType forValue(String value) {
    return map.getOrDefault(value, Unknown);
  }

  @JsonValue
  public String toValue() {
    return this.shortCode;
  }

}
