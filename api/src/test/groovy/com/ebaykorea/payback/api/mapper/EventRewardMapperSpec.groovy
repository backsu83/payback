package com.ebaykorea.payback.api.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.grocery.ApprovalGrocery.ApprovalEventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.ApprovalGrocery.ApprovalEventReward_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.EventPlatform_생성
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_FORMATTER

class EventRewardMapperSpec extends Specification {

  def mapper = Mappers.getMapper(EventRewardMapper.class)

  def "EventRewardRequestDto -> EventReward 매핑 확인"() {
    setup:
    def expirationDate = PaybackInstants.truncatedDays(PaybackInstants.now(), 30)

    expect:
    def result = mapper.map(EventRewardRequestDto_생성(saveAmount: 100, eventType: EventType.DailyCheckIn, budgetNo: 1L, eventNo: 2L, expirationDate: expirationDate, comment: "이벤트!"))
    result == EventPlatform_생성(saveAmount: 100, budgetNo: 1L, eventNo: 2L, expirationDate: expirationDate, comment: "이벤트!")
  }

  def "ApprovalEventReward 매핑 확인"() {
    expect:
    def result = mapper.map(1L, ApprovalEventRewardRequestDto_생성(shopManageCode: "GN", totalApprovalAmount: 10))
    result == ApprovalEventReward_생성(shopManageCode: "GN", saveAmount: 10)
  }
}
