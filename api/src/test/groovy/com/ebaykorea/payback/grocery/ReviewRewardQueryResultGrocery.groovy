package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult

import java.time.Instant

class ReviewRewardQueryResultGrocery {
  static def ReviewRewardQueryResult_생성(Map map = [:]) {
    new ReviewRewardQueryResult().tap {
      reviewType = (map.reviewType ?: "") as EventType
      save = (map.save ?: "") as String
      saveAmount = (map.saveAmount ?: 0L) as BigDecimal
      saveDate = (map.saveDate ?: null ) as Instant
    }
  }
}
