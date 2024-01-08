package com.ebaykorea.payback.api.mapper

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.TossEventRewardResponseDto_생성
import static com.ebaykorea.payback.grocery.TossEventRewardGrocery.*

class TossRewardMapperSpec extends Specification {
  def mapper = Mappers.getMapper(TossRewardMapper.class)

  def "TossEventRewardMapper가 정상 처리되는지 확인"() {
    expect:
    def responseResult = mapper.map(TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "FAILED", resultMessage: "resultMessage"))
    responseResult == TossRewardResponseDto_생성(transactionId: "1", resultCode: "FAILED", resultMessage: "resultMessage")

    def resultRequestResult = mapper.map(TossRewardResultRequestDto_생성())
    resultRequestResult == TossRewardRequestDto_생성()
  }
}
