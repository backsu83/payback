package com.ebaykorea.payback.scheduler.mapper

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.MassSaveRequestDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveQueueEntity_생성

class MassSaveRequestMapperSpec extends Specification {
  def mapper = Mappers.getMapper(MassSaveRequestMapper)

  def "MassSaveRequestMapper 확인"() {
    expect:
    def result = mapper.map(SmileCashSaveQueueEntity_생성(bizKey: "3", saveAmount: 10, reasonCode: "RM04Y"))
    result == MassSaveRequestDto_생성(shopOrderId: "3", amount: 10, shopManageCode: "RM04Y")
  }
}
