package com.ebaykorea.payback.core.domain.model;

import com.ebaykorea.payback.util.PaybackStrings;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Buyer {
  private final String buyerNo;
  private final String buyerId;
  private final String userKey;
  private final boolean member;
  private final boolean smileClubMember;

  //TODO: clubapi 연동 결과 저장이나 받아올 방법 고민
  private final Club club;

  public Optional<Club> findClub() {
    return Optional.ofNullable(club);
  }

  public boolean hasClub() {
    return findClub().isPresent();
  }
}
