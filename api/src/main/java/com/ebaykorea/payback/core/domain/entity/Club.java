package com.ebaykorea.payback.core.domain.entity;

import lombok.Value;

@Value
public class Club {
  String partnerId;
  String payCycleType;
  String membershipGrade;
}
