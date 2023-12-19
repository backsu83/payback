package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper


import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.EventRewardGrocery.SmileCashEvent_생성
import static com.ebaykorea.payback.grocery.CashEventRewardDtoGrocery.CashEventRewardResult_생성
import static com.ebaykorea.payback.grocery.CashEventRewardDtoGrocery.CashEventRewardRequest_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventRequestEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventResultEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate

class SmileCashEventRequestEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashEventEntityMapper.class)

  def "CashEventRewardRequest -> SmileCashEventEntity 매핑 테스트"() {
    expect:
    def result = mapper.map(
            CashEventRewardRequest_생성(
            requestNo: 1234L,
            requestId: "memberKey",
            saveAmount: 1000L,
            balanceCode: "G9",
            eventType: EventType.Toss
        ))

    result == SmileCashEventRequestEntity_생성(
        requestMoney: 1000L,
        requestOutputDisabledMoney: 1000L,
        cashBalanceType: "G9",
        smilecashCode: "A003",
        custNo: "memberKey",
        expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())),
        refNo: 1234L,
        ersNo: 8166,
        regId: "memberKey"
    )
  }

  def "SmileCashEventResultEntity -> CashEventRewardResult 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L,
        SmileCashEventResultEntity_생성(
            result: -1,
            comment: "중복처리",
            smilePayNo: 11L
        ))
    result == CashEventRewardResult_생성(
        requestNo: 1234L,
        smilePayNo: 11L,
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
}
