package com.ebaykorea.payback.core.domain.entity.cashback.buyer;

import lombok.Value;

@Value
public class Club {
  String partnerID;
  String payCycleType;
  String membershipGrade;
}
