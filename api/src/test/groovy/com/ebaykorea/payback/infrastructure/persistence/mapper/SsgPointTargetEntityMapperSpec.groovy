package com.ebaykorea.payback.infrastructure.persistence.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.grocery.SsgPointEntityGrocery.SsgPointTargetEntity_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointTargetResponseDto_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointUnit_준비상태_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPoint_생성
import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_UTC_FORMATTER

class SsgPointTargetEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SsgPointTargetEntityMapper)

  def "SsgPointTargetResponseDto 로 전환이 잘 되는지 확인한다"() {
    expect:
    def result = mapper.mapToSsgTarget(
        SsgPointTargetEntity_생성(
            orderDate: Instant.parse("2023-04-11T13:00:00.00Z"),
            scheduleDate: DATE_TIME_UTC_FORMATTER.parse("2023-04-16 13:00:00", Instant::from),
        )
    )

    result == SsgPointTargetResponseDto_생성()
  }

  def "SsgPointTargetEntity 로 전환이 잘 되는지 확인한다"() {
    expect:
    def ssgPoint = SsgPoint_생성()
    def ssgPointUnit = SsgPointUnit_준비상태_생성()
    def result = mapper.map(ssgPoint, ssgPointUnit)

    result == SsgPointTargetEntity_생성()
  }
}
