package com.ebaykorea.payback.core.domain.entity.cashback.buyer;

import java.util.Optional;
import lombok.*;

@Value
@Builder
public class Buyer {
  String buyerNo;
  String buyerId;
  String userKey;
  boolean member;
  boolean smileClubMember;
  Club club;

  public Optional<Club> findClub() {
    return Optional.ofNullable(club);
  }

  public boolean hasClub() {
    return findClub().isPresent();
  }
}
