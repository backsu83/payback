package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDetailDto
import com.ebaykorea.payback.api.dto.toss.TossEventRewardRequestDto
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResponseDto
import com.ebaykorea.payback.api.dto.toss.TossEventRewardResultRequestDto
import com.ebaykorea.payback.constant.TestConstant

import java.time.Instant

class TossEventRewardGrocery {

  static def TossEventRewardRequestDto_생성(Map map = [:]) {
    new TossEventRewardRequestDto().tap {
      requestId = (map.requestId ?: "1") as String
      userToken = (map.userToken ?: "userToken") as String
      amount = (map.amount ?: null) as BigDecimal
      message = (map.message ?: null) as String
      transactions = (map.transactions ?: null) as List<TossEventRewardRequestDetailDto>
    }
  }

  static def TossEventRewardRequestDetailDto_생성(Map map = [:]) {
    new TossEventRewardRequestDetailDto().tap{
      id = (map.id ?: "1") as String
      amount = (map.amount ?: 0) as BigDecimal
      transactAt = (map.transactAt ?: TestConstant.ORDER_DATE) as Instant
      cardApprovalNo = (map.cardApprovalNo ?: "cardApprovalNo") as String
      maskedCardNumber = (map.maskedCardNumber ?: "maskedCardNumber") as String
    }
  }

  static def TossEventRewardResponseDto_생성(Map map = [:]) {
    new TossEventRewardResponseDto().tap{
      transactionId = (map.transactionId ?: "") as String
      resultCode = (map.resultCode ?: "") as String
      resultMessage = (map.resultMessage ?: "") as String
    }
  }

  static def TossEventRewardResultRequestDto_생성(Map map = [:]) {
    new TossEventRewardResultRequestDto().tap{
      requestId = (map.requestId ?: "1") as String
      userToken = (map.userToken ?: "userToken") as String
    }
  }
}
