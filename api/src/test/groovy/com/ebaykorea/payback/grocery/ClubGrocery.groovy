package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.entity.cashback.member.Club

class ClubGrocery {
  static def club_생성(Map map = [:]) {
    new Club(
        (map.partnerId ?: "S001") as String,
        (map.payCycleType ?: "ANNL") as String,
        (map.membershipGrade ?: "BASC") as String,
    )
  }
}
