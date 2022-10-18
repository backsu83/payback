package com.ebaykorea.payback.core.domain.entity;

import lombok.*;

@Value
@Builder
public class Buyer {
  String buyerNo;
  String buyerId;
  String userKey;
  boolean member;
  boolean smileClubMember;
}
