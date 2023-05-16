package com.ebaykorea.payback.core.domain.constant;

import com.ebaykorea.payback.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointStatusType {
  Unknown("", ""),
  Ready("RR","대기"),
  Success("SS","성공"),
  Fail("FF","실패"),
  Cancel("SC","취소"),
  CacnelReady("RC","취소대기"),
  WithHold("WW","적립전취소"),
  Admin("MR","어드민 수동");

  private final String code;
  private final String description;

  @JsonCreator
  public static PointStatusType from(String pointStatusType) {
    return PaybackEnums.reverseMap(PointStatusType.class, PointStatusType::getCode)
        .getOrDefault(pointStatusType, Unknown);
  }
}
