package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.*
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.*

class SmileCashEventRequestEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashEventEntityMapper.class)

  def "SmileCashEvent -> SmileCashEventRequestEntity 매핑 테스트"() {
    expect:
    def result = mapper.map(request)

    result == expectResult

    where:
    desc                               | request                                                                                    | expectResult
    "토스 - expirationDate 없을때 기본 만료일자"  | TossEventReward_생성(requestNo: 1234L, saveAmount: 1000L)                                    | SmileCashEventRequestEntity_생성(requestMoney: 1000L, requestOutputDisabledMoney: 1000L, cashBalanceType: "G9", custNo: "memberKey", expireDate: Timestamp.from(request.getExpirationDate()), refNo: 1234L, ersNo: 8166, regId: "memberKey")
    "이벤트" | EventReward_생성(saveAmount: 100L, eventNo: 1L, budgetNo: 1L) | SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "G8", custNo: "memberKey", expireDate: Timestamp.from(request.getExpirationDate()), refNo: 1L, eid: 1L, regId: "memberKey", saveIntegrationType: "L")
    "이벤트 - expirationDate 있을때 해당 만료일자" | EventReward_생성(saveAmount: 100L, expirationDate: PaybackInstants.truncatedDays(PaybackInstants.now(), 30), eventNo: 1L, budgetNo: 1L) | SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "G8", custNo: "memberKey", expireDate: Timestamp.from(PaybackInstants.truncatedDays(PaybackInstants.now(), 30)), refNo: 1L, eid: 1L, regId: "memberKey", saveIntegrationType: "L")
    "렌터카 일반 상품평" | ReviewReward_생성(saveAmount: 100L, referenceType: ReviewReferenceType.Tour, reviewPromotionType: ReviewPromotionType.Normal, defaultComments: "여행 후기 기본 적립") | SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "GN", custNo: "memberKey", expireDate: Timestamp.from(request.getExpirationDate()), refNo: 1L, eid: 101L, ersNo: 102401, regId: "memberKey", saveIntegrationType: "L", comments: "여행 후기 기본 적립", orderNo: 1L)
    "코어 프리미엄 상품평" | ReviewReward_생성(saveAmount: 100L, eventType: EventType.ReviewPremium, reviewPromotionType: ReviewPromotionType.Premium) | SmileCashEventRequestEntity_생성(requestMoney: 100L, requestOutputDisabledMoney: 100L, cashBalanceType: "GP", custNo: "memberKey", expireDate: Timestamp.from(request.getExpirationDate()), refNo: 1L, eid: 100L,  ersNo: 102402, regId: "memberKey", saveIntegrationType: "L", orderNo: 1L)
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
        savingNo: 11L,
        resultCode: -1
    )
  }

  def "SmileCashEventEntity -> SmileCashEventResult 매핑 테스트"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    desc  | request                             | expectResult
    "진행중" | SmileCashEventEntity_생성()           | SmileCashEventResult_생성()
    "성공"  | SmileCashEventEntity_생성(status: 50) | SmileCashEventResult_생성(saved: true)
    "실패"  | SmileCashEventEntity_생성(status: 90) | SmileCashEventResult_생성(failed: true)
    "실패2" | SmileCashEventEntity_생성(status: 99) | SmileCashEventResult_생성(failed: true)
  }
}
