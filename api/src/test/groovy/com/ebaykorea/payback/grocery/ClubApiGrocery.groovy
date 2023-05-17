package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.ClubDataDto
import com.ebaykorea.payback.infrastructure.gateway.client.club.dto.SmileClubSubscriptionDto
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubMemberInfoDto
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.dto.SmileClubMemberResponseDto

class ClubApiGrocery {
    static def clubDataDto_생성(Map map = [:]) {
        new ClubDataDto(
            (map.member ?: new SmileClubSubscriptionDto(
                    (map.unifyMasterId ?: 23268003L) as Long,
                    (map.membershipGrade ?: "BASC") as String,
                    (map.payCycleType ?: "ANNL") as String,
                    (map.partnerId ?: "S001") as String,
                    (map.isUnifyMembership ?: true) as Boolean,
                    (map.isSSGMembership ?: true) as Boolean,
                    (map.isSSGPoint ?: true) as Boolean
            )) as SmileClubSubscriptionDto
        )
    }

    static def SmileClubMemberResponseDto_생성(Map map = [:]) {
        new SmileClubMemberResponseDto(
                (map.smileClubMember ?: true) as Boolean,
                (map.unifyMasterId ?: 23268003L) as Long,
                (map.smileClubMemberInformation ?: new SmileClubMemberInfoDto(
                        (map.statusCode ?: "SP") as String,
                        (map.membershipGrade ?: "BASC") as String,
                        (map.payCycleType ?: "ANNL") as String,
                        (map.joinPartnerId ?: "S001") as String,
                        (map.memberType ?: "P") as String,
                        (map.isUnifyMembership ?: true) as Boolean,
                        (map.isSSGMembership ?: true) as Boolean
                )) as SmileClubMemberInfoDto
        )
    }
}
