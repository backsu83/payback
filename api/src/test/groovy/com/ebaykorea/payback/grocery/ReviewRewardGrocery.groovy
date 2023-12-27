package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import com.ebaykorea.payback.core.dto.event.ReviewRewardRequestDto

class ReviewRewardGrocery {
  static def ReviewRewardRequestDto_생성(Map map = [:]) {
    new ReviewRewardRequestDto().tap {
      memberKey = (map.memberKey ?: "memberKey") as String
      requestNo = (map.requestNo ?: 1L) as Long
      saveAmount = (map.saveAmount ?: 10) as BigDecimal
      requestType = (map.requestType ?: ReviewReferenceType.Core) as ReviewReferenceType
    }
  }
}
