package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.ApprovalGrocery.ApprovalEventReward_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_FORMATTER

class SmileCashEventApprovalEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashEventApprovalEntityMapper.class)

  def "SmileCashEventApprovalEntityMapper 확인"() {
    expect:
    def result = mapper.map(ApprovalEventReward_생성(totalApprovalAmount: 10))
    result == SmileCashEventEntity_생성(
        status: 50,
        returnCode: "0000",
        certApprId: "authorizationId",
        saveAmount: 10,
        retExpireDate: Timestamp.from(DATE_FORMATTER.parse("2024-04-01", Instant::from)),
        saveDate: Timestamp.from(DATE_TIME_FORMATTER.parse("2024-03-27 00:00:00", Instant::from)))
  }
}
