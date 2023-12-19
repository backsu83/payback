package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto

import java.time.Instant

class MemberEventRewardDtoGrocery {
  static def EventRewardRequestDto_생성(Map map = [:]) {
    new EventRewardRequestDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      memberKey = (map.memberKey ?: "memberKey") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      eventType = (map.eventType ?: EventType.Unknown) as EventType
      eventId = (map.eventId ?: null) as String
      expirationDate = (map.expirationDate ?: null) as Instant
      comment = (map.comment ?: null) as String
    }
  }

  static def EventRewardResultDto_생성(Map map = [:]) {
    new EventRewardResultDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      smilePayNo = (map.smilePayNo ?: 0) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
