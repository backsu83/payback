package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.TossEventRewardGrocery.TossRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.TossRewardGrocery.EventRewardRequestEntity_생성

class TossRewardRequestMapperSpec extends Specification {
  def mapper = Mappers.getMapper(TossRewardRequestMapper)

  def "EventRewardRequestEntity 매핑 확인"() {
    expect:
    def result = mapper.map(1L , "gmarket", TossRewardRequestDto_생성(amount: 100))
    result.collect {
      it.requestNo
      it.requestId
      it.eventType
      it.userToken
      it.saveAmount
      it.message
      it.tenantId
    } == EventRewardRequestEntity_생성(saveAmount: 100).collect {
      it.requestNo
      it.requestId
      it.eventType
      it.userToken
      it.saveAmount
      it.message
      it.tenantId
    }
  }
}
