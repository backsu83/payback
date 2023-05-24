package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum PointTradeType {
  Unknown(null,null),
  Save("S","10"),
  Cancel("C","20");

  private final String code;
  private final String numberCode;

  @JsonCreator
  public static PointTradeType from(String pointTradeType) {
    return PaybackEnums
        .reverseMap(PointTradeType.class, PointTradeType::getCode)
        .getOrDefault(pointTradeType, Unknown);
  }
}
