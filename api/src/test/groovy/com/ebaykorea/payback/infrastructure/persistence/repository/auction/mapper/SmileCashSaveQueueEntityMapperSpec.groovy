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

class SmileCashSaveQueueEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def "SmileCashSaveQueueEntity 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1L, comment, request)
    result == expectResult

    where:
    desc                         | comment             | request                                                                                                                                        | expectResult
    "expirationDate 없을때 기본 만료일자" | "토스-신세계 유니버스 클럽 가입" | EventRewardRequestDto_생성(requestNo: 123L, saveAmount: 1000, eventType: EventType.Toss, comment: "additionalComment")                           | SmileCashSaveQueueEntity_생성(reasonCode: "RM02Y", reasonComment: "토스-신세계 유니버스 클럽 가입", bizType: 9, bizKey: "123", smileCashType: 2, saveAmount: 1000, expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())), insertOperator: "memberKey", additionalReasonComment: "additionalComment")
    "expirationDate 있을때 해당 만료일자" | null                | EventRewardRequestDto_생성(requestNo: 1L, saveAmount: 100, eventType: EventType.DailyCheckIn, eventNo: 1L, expirationDate: Instant.parse("2023-12-04T09:35:24.00Z")) | SmileCashSaveQueueEntity_생성(reasonCode: "RM01Y", bizType: 9, bizKey: "1", smileCashType: 2, saveAmount: 100, expireDate: Timestamp.from(Instant.parse("2023-12-04T09:35:24.00Z")), insertOperator: "memberKey", referenceKey: "1")
  }

  def "EventRewardResultDto 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L, -322, 12345L)
    result == EventRewardResultDto_생성(requestNo: 1234L, smilePayNo: 12345L, resultCode: -322)
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
}
