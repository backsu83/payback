package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.EventRewardGrocery.SmileCashEvent_생성
import static com.ebaykorea.payback.grocery.CashEventRewardDtoGrocery.CashEventRewardRequest_생성
import static com.ebaykorea.payback.grocery.CashEventRewardDtoGrocery.CashEventRewardResult_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate

class SmileCashSaveQueueEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def "SmileCashSaveQueueEntity 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1L ,
            CashEventRewardRequest_생성(
            requestNo: 123L,
            requestId: "memberKey",
            saveAmount: 1000,
            comment: "토스-신세계 유니버스 클럽 가입",
            eventType: EventType.Toss
        ))
    result == SmileCashSaveQueueEntity_생성(
        reasonCode: "RM02Y",
        reasonComment: "토스-신세계 유니버스 클럽 가입",
        additionalReasonComment: "토스-신세계 유니버스 클럽 가입",
        bizType: 9,
        bizKey: "123",
        smileCashType: 2,
        saveAmount: 1000,
        expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())),
        insertOperator: "memberKey"
    )
  }

  def "CashEventRewardResult 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L, -322, 12345L)
    result == CashEventRewardResult_생성(requestNo: 1234L, smilePayNo: 12345L, resultCode: -322)
  }

  def "SmileCashEvent 매핑 테스트"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    desc | request | expectResult
    "진행중" | SmileCashSaveQueueEntity_생성() | SmileCashEvent_생성()
    "성공" | SmileCashSaveQueueEntity_생성(saveStatus: 1) | SmileCashEvent_생성(saved: true)
    "실패" | SmileCashSaveQueueEntity_생성(saveStatus: 2) | SmileCashEvent_생성(failed: true)
  }
}
