package com.ebaykorea.payback.core.domain.entity.cashback.buyer;

import java.util.Optional;

import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.util.PaybackStrings;
import lombok.*;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.DOMAIN_ENTITY_001;

@Value
public class Member {
  String buyerNo;
  String buyerId;
  String userKey;
  boolean member;
  boolean smileClubMember;
  Club club;

  public static Member of(
      final String buyerNo,
      final String buyerId,
      final String userKey,
      final boolean member,
      final boolean smileClubMember,
      final Club club
  ) {
    return new Member(buyerNo, buyerId, userKey, member, smileClubMember, club);
  }

  private Member(
      final String buyerNo,
      final String buyerId,
      final String userKey,
      final boolean member,
      final boolean smileClubMember,
      final Club club
  ) {
    this.buyerNo = buyerNo;
    this.buyerId = buyerId;
    this.userKey = userKey;
    this.member = member;
    this.smileClubMember = smileClubMember;
    this.club = club;

    validate();
  }

  private void validate() {
    if (PaybackStrings.isBlank(buyerNo)) {
      throw new PaybackException(DOMAIN_ENTITY_001, "buyerNo 값이 없습니다");
    }
    if (PaybackStrings.isBlank(buyerId)) {
      throw new PaybackException(DOMAIN_ENTITY_001, "buyerId 값이 없습니다");
    }
    if (!member) {
      throw new PaybackException(DOMAIN_ENTITY_001, "회원이 아니면 캐시백 적립을 할 수 없습니다");
    }
  }

  public Optional<Club> findClub() {
    return Optional.ofNullable(club);
  }

  public boolean hasClub() {
    return findClub().isPresent();
  }
}
