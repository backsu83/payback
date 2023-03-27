package com.ebaykorea.payback.batch.domain.constant;

import static com.ebaykorea.payback.batch.domain.constant.PointTradeType.Unknown;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointStatusType {
  Unknown(null, "" ),
  Ready("RR","대기"),
  Success("SS","성공"),
  Fail("FF","실패"),
  Cancel("SC","취소"),
  CacnelReady("RC","취소대기"),
  WithHold("WW","보류"),
  Admin("MR","어드민 수동");

  private final String code;
  private final String description;

  @JsonCreator
  public static PointStatusType from(String siteType) {
    return PaybackEnums
        .reverseMap(PointStatusType.class, PointStatusType::getCode)
        .getOrDefault(siteType, Unknown);
  }
}
