package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto

class MemberEventRewardDtoGrocery {
  static def MemberEventRewardRequestDto_생성(Map map = [:]) {
    new MemberEventRewardRequestDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      eventType = (map.eventType ?: EventType.Unknown) as EventType
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
