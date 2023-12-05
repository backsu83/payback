package com.ebaykorea.payback.api.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.*
import static com.ebaykorea.payback.grocery.TossEventRewardGrocery.*

class TossRewardMapperSpec extends Specification {
  def mapper = Mappers.getMapper(TossRewardMapper.class)

  def "TossEventRewardMapper가 정상 처리되는지 확인"() {
    expect:
    def requestResult = mapper.map(TossRewardRequestDto_생성(amount: 1000, transactions: [TossRewardRequestDetailDto_생성(amount: 100)]))
    requestResult == TossEventRewardRequestDto_생성(saveAmount: 1000, details: [TossEventRewardRequestDetailDto_생성(eventAmount: 100)])

    def responseResult = mapper.map(TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "FAILED", resultMessage: "resultMessage"))
    responseResult == TossRewardResponseDto_생성(transactionId: "1", resultCode: "FAILED", resultMessage: "resultMessage")

    def resultRequestResult = mapper.map(TossRewardResultRequestDto_생성())
    resultRequestResult == TossEventRewardRequestDto_생성()
  }
}
