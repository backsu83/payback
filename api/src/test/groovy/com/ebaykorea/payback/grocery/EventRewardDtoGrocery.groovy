package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDetailDto
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.EventRewardResponseDto

import java.time.Instant

class EventRewardDtoGrocery {
  static def EventRewardRequestDto_생성(Map map = [:]) {
    new EventRewardRequestDto().tap{
      requestId = (map.requestId ?: "1") as String
      eventType = (map.eventType ?: EventType.Toss) as EventType
      userToken = (map.userToken ?: "userToken") as String
      saveAmount = (map.saveAmount ?: null) as BigDecimal
      message = (map.message ?: null) as String
      details = (map.details ?: null) as List<EventRewardRequestDetailDto>
    }
  }

  static def EventRewardRequestDetailDto_생성(Map map = [:]) {
    new EventRewardRequestDetailDto().tap{
      detailId = (map.detailId ?: "1") as String
      eventAmount = (map.eventAmount ?: 0) as BigDecimal
      eventDate = (map.eventDate ?: TestConstant.ORDER_DATE) as Instant
      cardApprovalNo = (map.cardApprovalNo ?: "cardApprovalNo") as String
      maskedCardNumber = (map.maskedCardNumber ?: "maskedCardNumber") as String
    }
  }
  static def EventRewardResponseDto_생성(Map map = [:]) {
    new EventRewardResponseDto().tap{
      smilePayNo = (map.smilePayNo ?: "") as String
      resultCode = (map.resultCode ?: "") as String
      resultMessage = (map.resultMessage ?: null) as String
    }
  }
}
