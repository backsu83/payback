package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

  private static transient Map<Integer, OrderSiteType> map = PaybackEnums.reverseMap(OrderSiteType.class, OrderSiteType::getCode);

  @JsonCreator
  public static OrderSiteType forValue(Integer value) {
    return map.getOrDefault(value, Unknown);
  }

  @JsonCreator
  public static OrderSiteType from(String siteType) {
    return PaybackEnums
        .reverseMap(OrderSiteType.class, OrderSiteType::getShortCode)
        .getOrDefault(siteType, Unknown);
  }

  @JsonValue
  public Integer toValue() {
    return this.code;
  }
}
