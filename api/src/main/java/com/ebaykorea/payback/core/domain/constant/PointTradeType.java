package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum PointTradeType {
  Unknown(null),
  Save("S"),
  Cancel("C");

  private final String code;

  @JsonCreator
  public static PointTradeType from(String pointTradeType) {
    return PaybackEnums
        .reverseMap(PointTradeType.class, PointTradeType::getCode)
        .getOrDefault(pointTradeType, Unknown);
  }
}
