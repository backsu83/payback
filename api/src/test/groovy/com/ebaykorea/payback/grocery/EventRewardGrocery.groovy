package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.entity.event.EventReward
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent

class EventRewardGrocery {
  static def EventReward_생성(Map map = [:]) {
    new EventReward(
        (map.requestNo ?: 1L) as Long,
        (map.requestId ?: "1") as String,
        (map.eventType ?: EventType.Toss) as EventType
    )
  }

  static def SmileCashEvent_생성(Map map = [:]) {
    new SmileCashEvent(
        (map.smilePayNo ?: 1L) as Long,
        (map.saved ?: false) as boolean,
        (map.failed ?: false) as boolean,
        (map.returnCode ?: null) as String,
    )
  }
}
