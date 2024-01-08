package com.ebaykorea.payback.grocery


import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardResponseDto

class EventRewardDtoGrocery {
  static def TossEventRewardResponseDto_생성(Map map = [:]) {
    new TossEventRewardResponseDto().tap{
      smilePayNo = (map.smilePayNo ?: "") as String
      resultCode = (map.resultCode ?: "") as String
      resultMessage = (map.resultMessage ?: null) as String
    }
  }
}
