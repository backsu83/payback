package com.ebaykorea.payback.batch.domain.constant;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
  Unknown(""),
  NormalMember("NormalMember"),
  SimpleMember("SimpleMember"),
  NonMember("NonMember");

  private final String code;

  private static final transient Map<String, MemberType> map = PaybackEnums.reverseMap(MemberType.class, MemberType::getCode);

  public static MemberType codeOf(String code) {
    return map.getOrDefault(code, Unknown);
  }
}
