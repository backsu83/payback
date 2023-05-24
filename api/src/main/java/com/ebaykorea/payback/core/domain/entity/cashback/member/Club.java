package com.ebaykorea.payback.core.domain.entity.cashback.member;

import com.ebaykorea.payback.core.domain.constant.SmileClubMemberType;
import com.ebaykorea.payback.util.PaybackBooleans;
import lombok.Value;

import static com.ebaykorea.payback.util.PaybackBooleans.asPrimitive;

@Value
public class Club {
  String partnerId;
  String payCycleType;
  String membershipGrade;
  Boolean isSmileClubMember;
  Boolean isSSGMembership;
  Boolean isSSGPoint;

  public boolean isSsgMembership() {
    return asPrimitive(isSSGMembership) && asPrimitive(isSmileClubMember) && asPrimitive(isSSGPoint);
  }
}
