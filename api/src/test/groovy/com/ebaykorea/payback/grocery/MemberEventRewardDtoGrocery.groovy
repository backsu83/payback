package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto

import java.time.Instant

class MemberEventRewardDtoGrocery {
  static def MemberEventRewardRequestDto_생성(Map map = [:]) {
    new MemberEventRewardRequestDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      memberKey = (map.memberKey ?: "memberKey") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      eventType = (map.eventType ?: EventType.Unknown) as EventType
      expirationDate = (map.expirationDate ?: null) as Instant
    }
  }

  static def MemberEventRewardResultDto_생성(Map map = [:]) {
    new MemberEventRewardResultDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      smilePayNo = (map.smilePayNo ?: 0) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
