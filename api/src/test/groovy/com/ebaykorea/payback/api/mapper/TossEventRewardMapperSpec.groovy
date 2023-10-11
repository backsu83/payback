package com.ebaykorea.payback.api.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.*
import static com.ebaykorea.payback.grocery.TossEventRewardGrocery.*

class TossEventRewardMapperSpec extends Specification {
  def mapper = Mappers.getMapper(TossEventRewardMapper.class)

  def "TossEventRewardMapper가 정상 처리되는지 확인"() {
    expect:
    def requestResult = mapper.map(TossEventRewardRequestDto_생성(amount: 1000, transactions: [TossEventRewardRequestDetailDto_생성(amount: 100)]))
    requestResult == EventRewardRequestDto_생성(saveAmount: 1000, details: [EventRewardRequestDetailDto_생성(eventAmount: 100)])

    def responseResult = mapper.map(EventRewardResponseDto_생성(smilePayNo: "1", resultCode: "FAILED", resultMessage: "resultMessage"))
    responseResult == TossEventRewardResponseDto_생성(transactionId: "1", resultCode: "FAILED", resultMessage: "resultMessage")

  }
}
