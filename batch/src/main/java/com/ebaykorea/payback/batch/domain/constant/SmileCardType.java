package com.ebaykorea.payback.batch.domain.constant;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmileCardType {
  Unknown(""),
  T0("T0"),
  T1("T1"),
  T2("T2"),
  T3("T3");

  private String code;

  private static final transient Map<String, SmileCardType> map = PaybackEnums.reverseMap(SmileCardType.class, SmileCardType::getCode);

  public static SmileCardType codeOf(final String code) {
    return map.getOrDefault(code, Unknown);
  }
}
