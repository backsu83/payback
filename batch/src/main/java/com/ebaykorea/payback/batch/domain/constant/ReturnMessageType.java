package com.ebaykorea.payback.batch.domain.constant;


import com.ebaykorea.payback.batch.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnMessageType {
  Unknown(""),
  SUCCESS("API0000"),
  EARN_DUPLICATED("PRC4051"),
  CANCEL_DUPLICATED("PRC4081");

  private String code;

  private static transient Map<String, ReturnMessageType> map = PaybackEnums.reverseMap(ReturnMessageType.class, ReturnMessageType::getCode);

  @JsonCreator
  public static ReturnMessageType codeOf(String value) {
    return map.getOrDefault(value, Unknown);
  }
}
