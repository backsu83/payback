package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDetailDto
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto

class EventRewardDtoGrocery {
  static def EventRewardRequestDto_생성(Map map = [:]) {
    new EventRewardRequestDto().tap{
      requestId = (map.requestId ?: "1") as String
      eventType = (map.eventType ?: EventType.Toss) as EventType
      userToken = (map.userToken ?: "userToken") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      details = (map.details ?: []) as List<EventRewardRequestDetailDto>
    }
  }
  static def EventRewardResponseDto_생성(Map map = [:]) {
    new EventRewardResponseDto().tap{
      saveProcessId = (map.saveProcessId ?: "") as String
      resultCode = (map.resultCode ?: "") as String
    }
  }
}
