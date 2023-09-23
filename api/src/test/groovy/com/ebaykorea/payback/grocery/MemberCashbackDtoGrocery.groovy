package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.MemberCashbackRequestDto
import com.ebaykorea.payback.core.dto.event.MemberCashbackResultDto

class MemberCashbackDtoGrocery {
  static def MemberCashbackRequestDto_생성(Map map = [:]) {
    new MemberCashbackRequestDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      eventType = (map.eventType ?: EventType.Unknown) as EventType
    }
  }

  static def MemberCashbackResultDto_생성(Map map = [:]) {
    new MemberCashbackResultDto().tap{
      requestNo = (map.requestNo ?: 0) as long
      smilePayNo = (map.smilePayNo ?: 0) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
