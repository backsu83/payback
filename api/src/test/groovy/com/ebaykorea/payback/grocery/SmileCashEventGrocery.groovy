package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import com.ebaykorea.payback.core.domain.entity.event.request.EventPlatform
import com.ebaykorea.payback.core.domain.entity.event.request.Review
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult
import com.ebaykorea.payback.core.domain.entity.event.request.Toss

import java.time.Instant

class SmileCashEventGrocery {
  static def Review_생성(Map map = [:]) {
    new Review(
        (map.requestNo ?: 1L) as Long,
        (map.memberKey ?: "memberKey") as String,
        (map.saveAmount ?: 0) as BigDecimal,
        (map.eventType ?: EventType.Review) as EventType,
        (map.referenceType ?: ReviewReferenceType.Core) as ReviewReferenceType,
        (map.reviewPromotionType ?: ReviewPromotionType.Unknown) as ReviewPromotionType,
        (map.defaultComments ?: "") as String,
    )
  }

  static def EventPlatform_생성(Map map = [:]) {
    new EventPlatform(
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

  static def Toss_생성(Map map = [:]) {
    Toss.of(
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
