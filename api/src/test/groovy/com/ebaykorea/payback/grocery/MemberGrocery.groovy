package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.entity.cashback.buyer.Club
import com.ebaykorea.payback.core.domain.entity.cashback.buyer.Member

class MemberGrocery {
  static def Member_생성(Map map = [:]) {
    Member.of(
        (map.buyerNo ?: "buyerNo") as String,
        (map.buyerId ?: "buyerId") as String,
        (map.userKey ?: "") as String,
        (map.member) as boolean,
        (map.smileClubMember) as boolean,
        (map.club ?: null) as Club
    )
  }

  static def 회원_생성(boolean smileClubMember = false, Club club = null) {
    Member_생성(member: true, smileClubMember: smileClubMember, club: club)
  }
}
