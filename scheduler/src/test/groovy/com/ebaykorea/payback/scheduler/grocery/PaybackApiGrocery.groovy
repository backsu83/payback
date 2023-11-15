package com.ebaykorea.payback.scheduler.grocery

import com.ebaykorea.payback.scheduler.client.dto.payback.CommonResponse
import com.ebaykorea.payback.scheduler.client.dto.payback.MemberEventRewardResponseDto
import com.ebaykorea.payback.scheduler.client.dto.payback.MemberEventRewardResultDto

class PaybackApiGrocery {
  static def CommonResponse_생성(Map map = [:]) {
    new CommonResponse<MemberEventRewardResponseDto>().tap {
      code = (map.code ?: 0) as int
      data = (map.data ?: MemberEventRewardResponseDto_생성(map)) as MemberEventRewardResponseDto
      message = (map.message ?: 1L) as String
    }
  }
  static def MemberEventRewardResponseDto_생성(Map map = [:]) {
    new MemberEventRewardResponseDto().tap {
      memberKey = (map.memberKey ?: "") as String
      eventRewardResult = (map.eventRewardResult ?: MemberEventRewardResultDto_생성(map)) as MemberEventRewardResultDto
    }
  }

  static def MemberEventRewardResultDto_생성(Map map = [:]) {
    new MemberEventRewardResultDto().tap {
      requestNo = (map.requestNo ?: 0L) as long
      smilePayNo = (map.smilePayNo ?: 0L) as long
      resultCode = (map.resultCode ?: 0) as int
    }
  }
}
