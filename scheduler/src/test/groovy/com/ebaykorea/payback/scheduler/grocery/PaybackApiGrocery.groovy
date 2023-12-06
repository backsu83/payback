package com.ebaykorea.payback.scheduler.grocery

import com.ebaykorea.payback.scheduler.client.dto.payback.CommonResponse
import com.ebaykorea.payback.scheduler.client.dto.payback.EventRewardResultDto

class PaybackApiGrocery {
  static def CommonResponse_생성(Map map = [:]) {
    new CommonResponse<EventRewardResultDto>().tap {
      code = (map.code ?: 0) as int
      data = (map.data ?: EventRewardResultDto_생성(map)) as EventRewardResultDto
      message = (map.message ?: 1L) as String
    }
  }

  static def EventRewardResultDto_생성(Map map = [:]) {
    new EventRewardResultDto().tap {
      requestNo = (map.requestNo ?: 0L) as long
      smilePayNo = (map.smilePayNo ?: 0L) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
