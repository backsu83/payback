package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.EventRewardGrocery.SmileCashEvent_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays

class SmileCashSaveQueueEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def "SmileCashSaveQueueEntity 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1L, comment, request)
    result == expectResult

    where:
    desc                         | comment             | request                                                                                                                                        | expectResult
    "expirationDate 없을때 기본 만료일자" | "토스-신세계 유니버스 클럽 가입" | EventRewardRequestDto_생성(requestNo: 123L, saveAmount: 1000, eventType: EventType.Toss, comment: "additionalComment")                           | SmileCashSaveQueueEntity_생성(reasonCode: "RM02Y", reasonComment: "토스-신세계 유니버스 클럽 가입", bizType: 9, bizKey: "123", smileCashType: 2, saveAmount: 1000, expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())), insertOperator: "memberKey", additionalReasonComment: "토스-신세계 유니버스 클럽 가입")
    "expirationDate 있을때 해당 만료일자" | null                | EventRewardRequestDto_생성(requestNo: 1L, saveAmount: 100, eventType: EventType.DailyCheckIn, eventNo: 1L, expirationDate: Instant.parse("2023-12-04T09:35:24.00Z")) | SmileCashSaveQueueEntity_생성(reasonCode: "RM03Y", bizType: 9, bizKey: "1", smileCashType: 2, saveAmount: 100, expireDate: Timestamp.from(Instant.parse("2023-12-04T09:35:24.00Z")), insertOperator: "memberKey", referenceKey: "1")
  }

  def "EventRewardResultDto 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L, -322, 12345L)
    result == EventRewardResultDto_생성(requestNo: 1234L, savingNo: 12345L, resultCode: -322)
  }

  def "SmileCashEvent 매핑 테스트"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    desc  | request                                    | expectResult
    "진행중" | SmileCashSaveQueueEntity_생성()              | SmileCashEvent_생성()
    "성공"  | SmileCashSaveQueueEntity_생성(saveStatus: 1) | SmileCashEvent_생성(saved: true)
    "실패"  | SmileCashSaveQueueEntity_생성(saveStatus: 2) | SmileCashEvent_생성(failed: true)
  }

  def "EventType에 별로 SmileCashSaveQueueEntity 맵핑 테스트"() {

    setup:
    def result = mapper.map(1L , "comment", request)

    expect:
    result == expectResult

    where:
    _________________________________________________
    desc | request
    "이벤트 - 토스 사후적립"    | EventRewardRequestDto_생성(eventType: EventType.Toss, memberKey: "user1", requestNo: 1L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 출석체크"         | EventRewardRequestDto_생성(eventType: EventType.DailyCheckIn, memberKey: "user2", requestNo: 2L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 상품평(일반)"     | EventRewardRequestDto_생성(eventType: EventType.Review, memberKey: "user3", requestNo: 3L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 상품평(프리미엄)" | EventRewardRequestDto_생성(eventType: EventType.ReviewPremium, memberKey: "user4", requestNo: 4L, saveAmount: 100L, eventNo: 100)
    _________________________________________________
    expectResult | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 1L, memberId: "user1", reasonCode: "RM02Y", reasonComment: "comment", additionalReasonComment: "comment",  bizType: "9", smileCashType: "2", insertOperator: "user1", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 30))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 2L, memberId: "user2", reasonCode: "RM03Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user2", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 90))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 3L, memberId: "user3", reasonCode: "RM04Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user3", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 4L, memberId: "user4", reasonCode: "RM05Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user4", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
  }
}
