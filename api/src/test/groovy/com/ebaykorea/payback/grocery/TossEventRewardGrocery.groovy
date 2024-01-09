package com.ebaykorea.payback.grocery


import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDetailDto
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto
import com.ebaykorea.payback.api.dto.toss.TossRewardResponseDto
import com.ebaykorea.payback.api.dto.toss.TossRewardResultRequestDto
import com.ebaykorea.payback.constant.TestConstant

import java.time.Instant

class TossEventRewardGrocery {

  static def TossRewardRequestDto_생성(Map map = [:]) {
    new TossRewardRequestDto().tap {
      requestId = (map.requestId ?: "1") as String
      userToken = (map.userToken ?: "userToken") as String
      amount = (map.amount ?: null) as BigDecimal
      message = (map.message ?: null) as String
      transactions = (map.transactions ?: null) as List<TossRewardRequestDetailDto>
    }
  }

  static def TossRewardResponseDto_생성(Map map = [:]) {
    new TossRewardResponseDto().tap{
      transactionId = (map.transactionId ?: "") as String
      resultCode = (map.resultCode ?: "") as String
      resultMessage = (map.resultMessage ?: "") as String
    }
  }

  static def TossRewardResultRequestDto_생성(Map map = [:]) {
    new TossRewardResultRequestDto().tap{
      requestId = (map.requestId ?: "1") as String
      userToken = (map.userToken ?: "userToken") as String
    }
  }
}
