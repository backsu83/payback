package com.ebaykorea.payback.core.domain.entity.cashback.member;

import com.ebaykorea.payback.core.domain.constant.SmileClubMemberType;
import lombok.Value;

@Value
public class Club {
  String partnerId;
  String payCycleType;
  String membershipGrade;
  Boolean isSSGMembership;
  SmileClubMemberType statusCode;

  public boolean isSsgMembership() {
    return isSSGMembership && (statusCode == SmileClubMemberType.Free || statusCode == SmileClubMemberType.Premium);
  }
}
