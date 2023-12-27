package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper


import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.EventRewardGrocery.SmileCashEvent_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventRequestEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventResultEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays

class SmileCashEventRequestEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashEventEntityMapper.class)

  def "EventRewardRequestDto -> SmileCashEventEntity 매핑 테스트"() {
    expect:
    def result = mapper.map(request)

    result == expectResult

    where:
    desc | request | expectResult
    "expirationDate 없을때 기본 만료일자" | EventRewardRequestDto_생성(requestNo: 1234L, saveAmount: 1000L, eventType: EventType.Toss)                                                           | SmileCashEventRequestEntity_생성(requestMoney: 1000L, requestOutputDisabledMoney: 1000L, cashBalanceType: "G9", custNo: "memberKey", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 30)), refNo: 1234L, ersNo: 8166, regId: "memberKey")
    "expirationDate 있을때 해당 만료일자" | EventRewardRequestDto_생성(requestNo: 1L, saveAmount: 100L, eventType: EventType.DailyCheckIn, expirationDate: Instant.parse("2023-12-04T09:35:24.00Z")) | SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "G8", custNo: "memberKey", expireDate: Timestamp.from(Instant.parse("2023-12-04T09:35:24.00Z")), refNo: 1L, regId: "memberKey")
  }

  def "SmileCashEventResultEntity -> EventRewardResultDto 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L,
        SmileCashEventResultEntity_생성(
            result: -1,
            comment: "중복처리",
            smilePayNo: 11L
        ))
    result == EventRewardResultDto_생성(
        requestNo: 1234L,
        rewardNo: 11L,
        resultCode: -1
    )
  }

  def "SmileCashEvent 매핑 테스트"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    desc | request | expectResult
    "진행중" | SmileCashEventEntity_생성()             | SmileCashEvent_생성()
    "성공" | SmileCashEventEntity_생성(status: 50) | SmileCashEvent_생성(saved: true)
    "실패" | SmileCashEventEntity_생성(status: 90) | SmileCashEvent_생성(failed: true)
    "실패2" | SmileCashEventEntity_생성(status: 99) | SmileCashEvent_생성(failed: true)
  }

  def "EventType에 별로 SmileCashEventRequestEntity 맵핑 테스트"() {

    setup:
    def result = mapper.map(request)

    expect:
    result == expectResult

    where:
    _________________________________________________
    desc | request
    "이벤트 - 토스 사후적립"    | EventRewardRequestDto_생성(eventType: EventType.Toss, requestNo: 1L, saveAmount: 1000L)
    "이벤트 - 출석체크"         | EventRewardRequestDto_생성(eventType: EventType.DailyCheckIn, requestNo: 1L, saveAmount: 100L)
    "이벤트 - 상품평(일반)"     | EventRewardRequestDto_생성(eventType: EventType.Review, requestNo: 1L, saveAmount: 100L)
    "이벤트 - 상품평(프리미엄)" | EventRewardRequestDto_생성(eventType: EventType.ReviewPremium, requestNo: 1L, saveAmount: 100L)
    _________________________________________________
    expectResult | _
    SmileCashEventRequestEntity_생성(requestMoney: 1000L, requestOutputDisabledMoney: 1000L, cashBalanceType: "G9", custNo: "memberKey", refNo: 1L, ersNo: 8166, regId: "memberKey", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 30))) | _
    SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "G8", custNo: "memberKey", refNo: 1L, regId: "memberKey", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 90))) | _
    SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "GN", custNo: "memberKey", refNo: 1L, regId: "memberKey", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
    SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "GP", custNo: "memberKey", refNo: 1L, regId: "memberKey", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
  }
}
