package com.ebaykorea.payback.infrastructure.gateway.mapper

import com.ebaykorea.payback.core.domain.constant.CashbackType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.OrderGrocery.*
import static com.ebaykorea.payback.grocery.PaymentGrocery.스마일페이_Payment_생성
import static com.ebaykorea.payback.grocery.RewardPolicyGrocery.*

class RewardPolicyGatewayMapperSpec extends Specification {
  def mapper = Mappers.getMapper(RewardPolicyGatewayMapper.class)

  def "RewardPolicyRequestDto으로의 매핑 확인"() {
    expect:
    def result = mapper.map(
        Order_생성(smileClubMember: true),
        스마일페이_Payment_생성(amount: 1000),
        ["itemSnapshotKey1": ItemSnapshot_생성(isSmileDelivery: true, isMoneyCategory: true)],
        ["orderUnitKey1": OrderUnitKey_생성()])

    result == RewardPolicyRequestDto_생성(totalPrice: 1000, club: true, details: [RewardPolicyDetailRequestDto_생성(itemType: "SD", price: 1000, redeemable: true)])
  }

  def "RewardPolicy으로의 매핑 확인"() {
    expect:
    def result = mapper.map(RewardPolicyDto_생성(
        t0SmileCardCashbackAmount: 1,
        t1t2SmileCardCashbackAmount: 2,
        t3SmileCardCashbackAmount: 3,
        smileCardAdditionalCashbackAmount: 4,
        type: "ChargePay",
        appliedSaveAmount: 5,
        nonClubSaveAmount: 6,
        clubSaveAmount: 7,
        appliedSaveRate: 8,
        nonClubSaveRate: 9,
        clubSaveRate: 10,
        nonClubMaxSaveAmount: 11,
        clubMaxSaveAmount: 12
    ))
    result == RewardPolicy_생성(
        t0SmileCardCashbackAmount: 1,
        t1t2SmileCardCashbackAmount: 2,
        t3SmileCardCashbackAmount: 3,
        smileCardAdditionalCashbackAmount: 4,
        type: CashbackType.ChargePay,
        appliedSaveAmount: 5,
        nonClubSaveAmount: 6,
        clubSaveAmount: 7,
        appliedSaveRate: 8,
        nonClubSaveRate: 9,
        clubSaveRate: 10,
        nonClubMaxSaveAmount: 11,
        clubMaxSaveAmount: 12
    )
  }
}
