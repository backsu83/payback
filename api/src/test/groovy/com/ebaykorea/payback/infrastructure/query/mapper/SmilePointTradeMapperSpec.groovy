package com.ebaykorea.payback.infrastructure.query.mapper

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SmilePointTradeGrocery.SmilePointTradeEntity_생성
import static com.ebaykorea.payback.grocery.SmilePointTradeGrocery.SmilePointTradeQueryResult_생성

class SmilePointTradeMapperSpec extends Specification {
  def smilePointTradeMapper = Mappers.getMapper(SmilePointTradeMapper)

  def "SmilePointTradeMapper가 의도한대로 동작하는지 확인"() {
    expect:
    def result = smilePointTradeMapper.map(List.of(SmilePointTradeEntity_생성()))

    result == [SmilePointTradeQueryResult_생성()]
  }
}
