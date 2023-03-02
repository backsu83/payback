package com.ebaykorea.payback.infrastructure.query.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SmilePointTradeQueryResult {
  private long smilePayNo;

  private String buyerNo;

  private int point;

  private long contrNo;

  private String comment;

  private int reasonCode;

  private int apprStatus;

  private String apprStatusName;
  private int targetType;

  private String targetTypeName;

  private String saveType;

  private String errorMsg;

  private String certApprId;

  private String userKey;

  private String regDate;

  private String expireDate;


  public String getApprStatusName() {
    String result = String.valueOf(this.apprStatus);
    switch (apprStatus) {
      case 0:
      case 10:
      case 20:
      case 30:
      case 40:
        result = "처리중";
        break;
      case 50:
        result = "성공";
        break;
      case 90:
      case 99:
        result = "실패";
        break;
    }
    return result;
  }

  public String getTargetTypeName() {
    String result = String.valueOf(this.targetType);
    switch (targetType) {
      case 10:
        result = "API";
        break;
      case 20:
        result = "Batch";
        break;
      case 99:
        result = "ETC";
        break;
    }
    return result;
  }
}
