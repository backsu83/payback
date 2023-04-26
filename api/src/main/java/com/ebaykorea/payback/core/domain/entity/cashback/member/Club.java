package com.ebaykorea.payback.core.domain.entity.cashback.member;

import lombok.Value;

@Value
public class Club {
  String partnerId;
  String payCycleType;
  String membershipGrade;
  Boolean isSSGMembership;
  String statusCode;

  public boolean isSsgMembership() {
    return isSSGMembership && (statusCode.equals("SF") || statusCode.equals("SP"));
  }
}
