package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmileClubMemberType {
  Unknown(null),
  Free("SF"),
  Premium("SP");

  private String code;

  private static transient Map<String, SmileClubMemberType> map = PaybackEnums.reverseMap(SmileClubMemberType.class, SmileClubMemberType::getCode);

  @JsonCreator
  public static SmileClubMemberType forValue(String value) {
    return map.getOrDefault(value, Unknown);
  }

}
