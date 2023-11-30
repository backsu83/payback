package com.ebaykorea.payback.infrastructure.query

import com.ebaykorea.payback.config.properties.SaturnApplicationProperties
import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.gateway.TransactionGatewayImpl
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.CashbackOrderRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmilecardCashbackOrderRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.ssgpoint.SsgPointTargetRepository
import com.ebaykorea.payback.infrastructure.query.gmkt.GmarketRewardTargetQuery
import com.ebaykorea.payback.infrastructure.query.mapper.RewardTargetQueryMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.temporal.ChronoUnit

import static com.ebaykorea.payback.grocery.CashbackEntityGrocery.CashbackOrderEntity_생성
import static com.ebaykorea.payback.grocery.CashbackEntityGrocery.SmilecardCashbackOrderEntity_생성
import static com.ebaykorea.payback.grocery.RewardTargetQueryResultGrocery.CashbackTargetQueryData_생성
import static com.ebaykorea.payback.grocery.RewardTargetQueryResultGrocery.RewardTargetQueryResult_생성
import static com.ebaykorea.payback.grocery.RewardTargetQueryResultGrocery.SmileCardQueryData_생성
import static com.ebaykorea.payback.grocery.RewardTargetQueryResultGrocery.SsgPointTargetQueryData_생성
import static com.ebaykorea.payback.grocery.SsgPointEntityGrocery.SsgPointTargetEntity_생성

class GmarketRewardTargetQuerySpec extends Specification {
  def transactionGateway = Stub(TransactionGatewayImpl)

  def ssgPointTargetRepository = Stub(SsgPointTargetRepository)
  def smilecardCashbackOrderRepository = Stub(SmilecardCashbackOrderRepository)
  def cashbackOrderRepository = Stub(CashbackOrderRepository)

  def rewardTargetQueryMapper = Mappers.getMapper(RewardTargetQueryMapper)
  def saturnApplicationProperties = Stub(SaturnApplicationProperties)

  def cashbackQuery = new GmarketRewardTargetQuery(transactionGateway, ssgPointTargetRepository, smilecardCashbackOrderRepository, cashbackOrderRepository, rewardTargetQueryMapper, saturnApplicationProperties)

  def "올바른 쿼리 결과가 생성되는지 확인"() {
    setup:
    saturnApplicationProperties.getSiteCode() >> "G"
    smilecardCashbackOrderRepository.findById(_ as Long) >> Optional.ofNullable(스마일카드적립대상)
    ssgPointTargetRepository.findByPackNo(_ as Long) >> SSG적립대상
    cashbackOrderRepository.findByPackNo(_ as Long) >> 캐시백적립대상

    expect:
    def result = cashbackQuery.getSavedCashback(1L)
    result == expectResult

    where:
    _________________________________________________
    desc | 스마일카드적립대상
    "적립대상이 없는 경우" | null
    "적립대상이 있는 경우" | SmilecardCashbackOrderEntity_생성(cashbackAmount: 100, applyYn: "Y", t2t3CashbackAmount: 200, t2t3ApplyYn: "Y")
    "적립 데이터는 있지만 적립 대상이 아닌 경우" | SmilecardCashbackOrderEntity_생성(cashbackAmount: 100, applyYn: "N", t2t3CashbackAmount: 200, t2t3ApplyYn: "N")
    "스마일카드 T2 금액은 있지만 대상이 아닌 경우" | SmilecardCashbackOrderEntity_생성(cashbackAmount: 100, applyYn: "Y", t2t3CashbackAmount: 200, t2t3ApplyYn: "N")
    "SSGPOINT 적립 대상은 적립 예정일 별로 리턴" | null
    _________________________________________________
    SSG적립대상 | 캐시백적립대상
    [] | []
    [SsgPointTargetEntity_생성(saveAmount: 100), SsgPointTargetEntity_생성(saveAmount: 200)] | [CashbackOrderEntity_생성(amount: 100), CashbackOrderEntity_생성(amount: 200), CashbackOrderEntity_생성(cashbackType: "P", amount: 200), CashbackOrderEntity_생성(cashbackType: "P", amount: 200), CashbackOrderEntity_생성(cashbackType: "S"), CashbackOrderEntity_생성(cashbackType: "A"), CashbackOrderEntity_생성(cashbackType: "D")]
    [SsgPointTargetEntity_생성(saveAmount: 100, pointStatus: "WW")] | [CashbackOrderEntity_생성(amount: 100, tradeCd: "SVC"), CashbackOrderEntity_생성(cashbackType: "P", amount: 200, cancelYn: "Y")]
    [] | []
    [SsgPointTargetEntity_생성(scheduleDate: TestConstant.SSGPOINT_ORDER_DATE, saveAmount: 300), SsgPointTargetEntity_생성(saveAmount: 100), SsgPointTargetEntity_생성(saveAmount: 100)] | []
    _________________________________________________
    expectResult | _
    RewardTargetQueryResult_생성() | _
    RewardTargetQueryResult_생성(smileCard: SmileCardQueryData_생성(saveAmount: 100, expectSaveDays: 10, additionalSaveAmount: 200, additionalExpectSaveDate: TestConstant.ORDER_DATE.plus(30, ChronoUnit.DAYS)), ssgPoints: [SsgPointTargetQueryData_생성(expectSaveDate: TestConstant.SSGPOINT_SCHEDULE_DATE.truncatedTo(ChronoUnit.DAYS), saveAmount: 300, saved: true)], cashbackTargets: [CashbackTargetQueryData_생성(cashbackType: "SmilePay", saveAmount: 400), CashbackTargetQueryData_생성(cashbackType: "ChargePay", saveAmount: 1000), CashbackTargetQueryData_생성(cashbackType: "Seller", saveAmount: 1000), CashbackTargetQueryData_생성(cashbackType: "ClubDay", saveAmount: 1000), CashbackTargetQueryData_생성(cashbackType: "Item", saveAmount: 300)]) | _
    RewardTargetQueryResult_생성() | _
    RewardTargetQueryResult_생성(smileCard: SmileCardQueryData_생성(saveAmount: 100, expectSaveDays: 10)) | _
    RewardTargetQueryResult_생성(ssgPoints: [SsgPointTargetQueryData_생성(expectSaveDate: TestConstant.SSGPOINT_ORDER_DATE.truncatedTo(ChronoUnit.DAYS), saveAmount: 300, saved: true), SsgPointTargetQueryData_생성(expectSaveDate: TestConstant.SSGPOINT_SCHEDULE_DATE.truncatedTo(ChronoUnit.DAYS), saveAmount: 200, saved: true)]) | _
  }
}
