package com.ebaykorea.payback.batch.domain.constant;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopType {
  Unknown(null),
  SmileDelivery("SD"),
  SmileFresh("SF");

  private String code;

  private static final transient Map<String, ShopType> map = PaybackEnums.reverseMap(ShopType.class, ShopType::getCode);

  public static ShopType codeOf(final String code) {
    return map.getOrDefault(code, Unknown);
  }
}
