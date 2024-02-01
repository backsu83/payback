package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.*
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate

class SmileCashSaveQueueEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def "SmileCashEvent -> SmileCashSaveQueueEntity 매핑 테스트"() {
    expect:
    def result = mapper.map(1L, comment, request)
    result == expectResult

    where:
    desc                               | comment             | request                                                                                                              | expectResult
    "토스 - expirationDate 없을때 기본 만료일자"  | "토스-신세계 유니버스 클럽 가입" | TossEventReward_생성(requestNo: 123L, saveAmount: 1000)                                                                | SmileCashSaveQueueEntity_생성(reasonCode: "RM02Y", reasonComment: "토스-신세계 유니버스 클럽 가입", bizType: 9, bizKey: "123", smileCashType: 2, saveAmount: 1000, expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())), insertOperator: "memberKey", additionalReasonComment: "")
    "이벤트 - expirationDate 있을때 해당 만료일자" | "이벤트"               | EventReward_생성(saveAmount: 100, eventNo: 1L, expirationDate: Instant.parse("2023-12-04T09:35:24.00Z"), budgetNo: 1L) | SmileCashSaveQueueEntity_생성(reasonCode: "RM03Y", bizType: 9, bizKey: "1", smileCashType: 2, saveAmount: 100, expireDate: Timestamp.from(Instant.parse("2023-12-04T09:35:24.00Z")), insertOperator: "memberKey", referenceKey: "1", reasonComment: "이벤트", additionalReasonComment: "", budgetNo: 1L, saveStatus: 3)
    "일반 상품평"                           | "일반 상품평"            | ReviewReward_생성(saveAmount: 100)                                                                                     | SmileCashSaveQueueEntity_생성(reasonCode: "RM04Y", bizType: 9, bizKey: "1", smileCashType: 2, saveAmount: 100, expireDate: Timestamp.from(request.getExpirationDate()), insertOperator: "memberKey", referenceKey: "100", saveStatus: 3, reasonComment: "일반 상품평", additionalReasonComment: "(주문번호: 1)")
    "코어 프리미엄 상품평"                      | ""                  | ReviewReward_생성(saveAmount: 100, eventType: EventType.ReviewPremium)                                                 | SmileCashSaveQueueEntity_생성(reasonCode: "RM05Y", bizType: 9, bizKey: "1", smileCashType: 2, saveAmount: 100, expireDate: Timestamp.from(request.getExpirationDate()), insertOperator: "memberKey", referenceKey: "100", saveStatus: 3, additionalReasonComment: "(주문번호: 1)")
  }

  def "EventRewardResultDto 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L, -322, 12345L)
    result == EventRewardResultDto_생성(requestNo: 1234L, savingNo: 12345L, resultCode: -322)
  }

  def "SmileCashSaveQueueEntity -> SmileCashEventResult 매핑 테스트"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    desc  | request                                    | expectResult
    "진행중" | SmileCashSaveQueueEntity_생성()              | SmileCashEventResult_생성()
    "성공"  | SmileCashSaveQueueEntity_생성(saveStatus: 1) | SmileCashEventResult_생성(saved: true)
    "실패"  | SmileCashSaveQueueEntity_생성(saveStatus: 2) | SmileCashEventResult_생성(failed: true)
  }
}
