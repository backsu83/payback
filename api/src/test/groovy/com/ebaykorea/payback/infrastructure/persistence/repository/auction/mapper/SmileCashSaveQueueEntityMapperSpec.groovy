package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.MemberCashbackDtoGrocery.MemberCashbackRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberCashbackDtoGrocery.MemberCashbackResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate

class SmileCashSaveQueueEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def "SmileCashSaveQueueEntity 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1L , "memberKey",
        MemberCashbackRequestDto_생성(
            requestNo: 123L,
            saveAmount: 1000,
            eventType: EventType.Toss
        ))
    result == SmileCashSaveQueueEntity_생성(
        reasonCode: "RM02Y",
        reasonComment: "토스 사후 적립(이벤트성)",
        additionalReasonComment: "토스 사후 적립(이벤트성)",
        bizType: 9,
        bizKey: "123",
        smileCashType: 2,
        saveAmount: 1000,
        expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())),
        insertOperator: "memberKey"
    )
  }

  def "MemberCashbackResultDto 으로의 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L, -322, 12345L)
    result == MemberCashbackResultDto_생성(requestNo: 1234L, smilePayNo: 12345L, resultCode: -322)
  }
}
