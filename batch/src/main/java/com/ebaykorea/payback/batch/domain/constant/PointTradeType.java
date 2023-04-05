package com.ebaykorea.payback.batch.domain.constant;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointTradeType {
  Unknown(null),
  Save("S"),
  Cancel("C");

  private final String code;

  @JsonCreator
  public static PointTradeType from(String siteType) {
    return PaybackEnums
        .reverseMap(PointTradeType.class, PointTradeType::getCode)
        .getOrDefault(siteType, Unknown);
  }
}
