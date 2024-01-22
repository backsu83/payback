package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import com.ebaykorea.payback.core.domain.entity.event.EventReward
import com.ebaykorea.payback.core.domain.entity.event.ReviewReward
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEventResult
import com.ebaykorea.payback.core.domain.entity.event.TossEventReward

import java.time.Instant

class SmileCashEventGrocery {
  static def ReviewReward_생성(Map map = [:]) {
    new ReviewReward(
        (map.requestNo ?: 1L) as Long,
        (map.memberKey ?: "memberKey") as String,
        (map.saveAmount ?: 0) as BigDecimal,
        (map.eventType ?: EventType.Review) as EventType,
        (map.referenceType ?: ReviewReferenceType.Core) as ReviewReferenceType
    )
  }

  static def EventReward_생성(Map map = [:]) {
    new EventReward(
        (map.requestNo ?: 1L) as Long,
        (map.memberKey ?: "memberKey") as String,
        (map.saveAmount ?: 0) as BigDecimal,
        (map.eventType ?: EventType.DailyCheckIn) as EventType,
        (map.expirationPeriod ?: 90) as int,
        (map.eventNo ?: 0L) as Long,
        (map.budgetNo ?: 0L) as Long,
        (map.expirationDate ?: null) as Instant,
        (map.comment ?: "") as String,
    )
  }

  static def TossEventReward_생성(Map map = [:]) {
    TossEventReward.of(
        (map.requestNo ?: 1L) as Long,
        (map.memberKey ?: "memberKey") as String,
        (map.saveAmount ?: 0) as BigDecimal,
    )
  }

  static def SmileCashEventResult_생성(Map map = [:]) {
    new SmileCashEventResult(
        (map.smilePayNo ?: 1L) as Long,
        (map.saved ?: false) as boolean,
        (map.failed ?: false) as boolean,
        (map.returnCode ?: null) as String,
    )
  }
}
