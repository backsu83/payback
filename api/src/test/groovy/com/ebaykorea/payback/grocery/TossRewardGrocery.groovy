package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.entity.event.TossRewardRequestResult

class TossRewardGrocery {
  static def TossRewardRequestResult_생성(Map map = [:]) {
    new TossRewardRequestResult(
        (map.requestNo ?: 1L) as Long,
        (map.requestId ?: "1") as String,
        (map.eventType ?: EventType.Toss) as EventType
    )
  }
}
