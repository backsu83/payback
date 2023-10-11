package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper


import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.MemberEventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.MemberEventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventResultEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.getDefaultEnableDate

class SmileCashEventEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashEventEntityMapper.class)

  def "MemberCashbackRequestDto -> SmileCashEventEntity 매핑 테스트"() {
    expect:
    def result = mapper.map("memberKey",
        MemberEventRewardRequestDto_생성(
            requestNo: 1234L,
            saveAmount: 1000L,
            eventType: EventType.Toss
        ))

    result == SmileCashEventEntity_생성(
        requestMoney: 1000L,
        requestOutputDisabledMoney: 1000L,
        cashBalanceType: "G9",
        custNo: "memberKey",
        expireDate: Timestamp.from(getDefaultEnableDate(PaybackInstants.now())),
        refNo: 1234L,
        ersNo: 8166,
        regId: "memberKey"
    )
  }

  def "SmileCashEventResultEntity -> MemberCashbackResultDto 매핑 테스트"() {
    expect:
    def result = mapper.map(1234L,
        SmileCashEventResultEntity_생성(
            result: -1,
            comment: "중복처리",
            smilePayNo: 11L
        ))
    result == MemberEventRewardResultDto_생성(
        requestNo: 1234L,
        smilePayNo: 11L,
        resultCode: -1
    )

  }
}
