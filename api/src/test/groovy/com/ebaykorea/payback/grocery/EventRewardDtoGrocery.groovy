package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDetailDto
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDto

import java.time.Instant

class EventRewardDtoGrocery {
  static def TossEventRewardRequestDto_생성(Map map = [:]) {
    new TossEventRewardRequestDto().tap{
      requestId = (map.requestId ?: "1") as String
      eventType = (map.eventType ?: EventType.Toss) as EventType
      userToken = (map.userToken ?: "userToken") as String
      saveAmount = (map.saveAmount ?: null) as BigDecimal
      message = (map.message ?: null) as String
      details = (map.details ?: null) as List<TossEventRewardRequestDetailDto>
    }
  }

  static def TossEventRewardRequestDetailDto_생성(Map map = [:]) {
    new TossEventRewardRequestDetailDto().tap{
      detailId = (map.detailId ?: "1") as String
      eventAmount = (map.eventAmount ?: 0) as BigDecimal
      eventDate = (map.eventDate ?: TestConstant.ORDER_DATE) as Instant
      cardApprovalNo = (map.cardApprovalNo ?: "cardApprovalNo") as String
      maskedCardNumber = (map.maskedCardNumber ?: "maskedCardNumber") as String
    }
  }
  static def TossEventRewardResponseDto_생성(Map map = [:]) {
    new TossEventRewardResponseDto().tap{
      smilePayNo = (map.smilePayNo ?: "") as String
      resultCode = (map.resultCode ?: "") as String
      resultMessage = (map.resultMessage ?: null) as String
    }
  }
}
