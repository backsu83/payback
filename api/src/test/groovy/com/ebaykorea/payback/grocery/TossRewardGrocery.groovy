package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.entity.event.request.TossRewardRequestResult
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestEntity

class TossRewardGrocery {
  static def TossRewardRequestResult_생성(Map map = [:]) {
    new TossRewardRequestResult(
        (map.requestNo ?: 1L) as Long,
        (map.requestId ?: "1") as String,
        (map.eventType ?: EventType.Toss) as EventType
    )
  }

  static def EventRewardRequestEntity_생성(Map map = [:]) {
    new EventRewardRequestEntity().tap {
      requestNo = (map.requestNo ?: 1L) as Long
      requestId = (map.requestId ?: "1") as String
      eventType = (map.eventType ?: EventType.Toss) as EventType
      userToken = (map.userToken ?: "userToken") as String
      saveAmount = (map.saveAmount ?: null) as BigDecimal
      message = (map.message ?: null) as String
      tenantId = (map.tenantId ?: "gmarket") as String
    }
  }
}
