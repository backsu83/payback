package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReferenceType
import com.ebaykorea.payback.core.dto.event.CashEventRewardReqest
import com.ebaykorea.payback.core.dto.event.CashEventRewardResult

class CashEventRewardDtoGrocery {

  static def CashEventRewardRequest_생성(Map map = [:]) {
    new CashEventRewardReqest().tap{
      requestNo = (map.requestNo ?: 0) as long
      requestId = (map.requestId ?: "") as String
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      balanceCode = (map.balanceCode ?: "") as String
      comment = (map.comment ?: "") as String
      caller = (map.caller ?: ReferenceType.Unknown) as ReferenceType
      eventType = (map.eventType ?: EventType.Unknown) as EventType
    }
  }

  static def CashEventRewardResult_생성(Map map = [:]) {
    new CashEventRewardResult().tap{
      requestNo = (map.requestNo ?: 0) as long
      smilePayNo = (map.smilePayNo ?: 0) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
