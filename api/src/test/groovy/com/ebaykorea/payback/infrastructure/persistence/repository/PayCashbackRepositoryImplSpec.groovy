package com.ebaykorea.payback.infrastructure.persistence.repository

import com.ebaykorea.payback.infrastructure.persistence.mapper.CashbackOrderDetailEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.mapper.CashbackOrderEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.mapper.ChargePayPolicyEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.mapper.ClubDayPolicyEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.mapper.DefaultCashbackPolicyEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderDetailRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderPolicyRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderRepository
import com.ebaykorea.payback.util.support.Conditioner
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.CashbackEntityGrocery.CashbackOrderPolicyEntity_생성
import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.ChargePayCashback_생성
import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.ClubDayCashback_생성
import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.ItemCashback_생성
import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.SellerCashback_생성
import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.SmilePayCashback_생성
import static com.ebaykorea.payback.grocery.PayCashbackGrocery.Cashback_생성
import static com.ebaykorea.payback.grocery.PayCashbackGrocery.PayCashback_생성


class PayCashbackRepositoryImplSpec extends Specification {
  def cashbackOrderRepository = Stub(CashbackOrderRepository)
  def cashbackOrderPolicyRepository = Stub(CashbackOrderPolicyRepository)
  def cashbackOrderDetailRepository = Stub(CashbackOrderDetailRepository)
  def cashbackOrderEntityMapper = Mappers.getMapper(CashbackOrderEntityMapper)

  def chargePayPolicyEntityMapper = Mappers.getMapper(ChargePayPolicyEntityMapper)
  def clubDayPolicyEntityMapper = Mappers.getMapper(ClubDayPolicyEntityMapper)
  def defaultCashbackPolicyEntityMapper = Mappers.getMapper(DefaultCashbackPolicyEntityMapper)
  def cashbackOrderDetailEntityMapper = Mappers.getMapper(CashbackOrderDetailEntityMapper)

  def conditioner = Conditioner.of([
      chargePayPolicyEntityMapper,
      clubDayPolicyEntityMapper,
      defaultCashbackPolicyEntityMapper
  ])

  def repository = new PayCashbackRepositoryImpl(
      cashbackOrderRepository,
      cashbackOrderPolicyRepository,
      cashbackOrderDetailRepository,
      cashbackOrderEntityMapper,
      conditioner,
      cashbackOrderDetailEntityMapper
  )

  //TODO
  def "저장 호출이 잘 되는지 확인"() {

  }

  def "CashbackOrderPolicyEntity 변환이 잘되는지 확인"() {
    expect:
    def result = repository.mapToPolicies(payCashback, cashback, cashback.findAppliedCashbackPolicies())
    result == expectResult

    where:
    _________________________________________________
    desc | payCashback | cashback
    "적용대상이 아닐 경우 미생성" | PayCashback_생성() | Cashback_생성(cashbackUnits: [ItemCashback_생성(), SellerCashback_생성(), SmilePayCashback_생성(), ChargePayCashback_생성(), ClubDayCashback_생성()])
    "아이템캐시백" | PayCashback_생성() | Cashback_생성(cashbackUnits: [ItemCashback_생성(isSmilePay: true, maxLimitMoney: 1L)])
    "판매자캐시백" | PayCashback_생성() | Cashback_생성(cashbackUnits: [SellerCashback_생성(amount: 1000L)])
    "스마일페이캐시백" | PayCashback_생성() | Cashback_생성(cashbackUnits: [SmilePayCashback_생성(isSmilePay: true, saveRate: 1L)])
    "자동충전캐시백" | PayCashback_생성() | Cashback_생성(cashbackUnits: [ChargePayCashback_생성(isChargePay: true, chargePaySaveRate: 1L, chargePayClubSaveRate: 2L, chargePayMaxMoney: 3L, chargePayClubMaxMoney: 4L)])
    "클럽데이캐시백" | PayCashback_생성() | Cashback_생성(cashbackUnits: [ClubDayCashback_생성(isSmilePay: true, isClubMember: true, clubDayMaxSaveMoney: 1L, clubDayMaxSaveRate: 2L)])
    _________________________________________________
    expectResult | _ | _
    [] | _ | _
    [CashbackOrderPolicyEntity_생성(policyNo: 1L, maxLimitMoney: 1L)] | _ | _
    [CashbackOrderPolicyEntity_생성(type: "S", policyNo: 0L, name: "판매자 제공 적립")] | _ | _
    [CashbackOrderPolicyEntity_생성(type: "P", policyNo: 1L, subType: "P", saveRate: 1L, maxLimitMoney: 0L)] | _ | _
    [CashbackOrderPolicyEntity_생성(type: "A", policyNo: 1L, subType: "P", maxLimitMoney: 0L, chargePaySaveRate: 1L, chargePayClubSaveRate: 2L, chargePayMaxMoney: 3L, chargePayClubMaxMoney: 4L)] | _ | _
    [CashbackOrderPolicyEntity_생성(type: "D", policyNo: 1L, subType: "P", maxLimitMoney: 0L, clubDayMaxSaveMoney: 1L, clubDayMaxSaveRate: 2L)] | _ | _
  }
}
