package com.ebaykorea.payback.infrastructure.gateway

import com.ebaykorea.payback.core.domain.constant.CashbackType
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseResponse
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseReturn
import com.ebaykorea.payback.infrastructure.gateway.mapper.RewardGatewayMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.OrderGrocery.ItemSnapshot_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.OrderUnitKey_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.OrderUnit_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.Order_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.PaymentMethodSub_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.스마일페이_Payment_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackInfoDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackRequestDataDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackResponseDataDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackRewardBackendResponseDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackRewardGoodRequestDto_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardBackendCashbackPolicy_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardCashbackPolicies_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardCashbackPolicy_생성

class RewardGatewaySpec extends Specification {
  def rewardApiClient = Stub(RewardApiClient)
  def rewardGatewayMapper = Mappers.getMapper(RewardGatewayMapper)
  def rewardGatewayImpl = new RewardGatewayImpl(rewardApiClient, rewardGatewayMapper)

  def "RewardCashbackPolicies 결과가 정상인지 확인한다"() {
    setup:
    def rewardBase = RewardBaseReturn.builder().returnCode("0000").build()
    rewardApiClient.getCashbackReward(_ as CashbackRewardRequestDto) >> Optional.of(new RewardBaseResponse(rewardBase, cashbackResponse))
    rewardApiClient.getCashbackRewardBackend(_ as CashbackRewardRequestDto) >> Optional.of(new RewardBaseResponse(rewardBase, cashbackBackendResponse))

    expect:
    def result = rewardGatewayImpl.getCashbackPolicies(Order_생성(), 스마일페이_Payment_생성(), ["itemSnapshotKey1": ItemSnapshot_생성()], ["orderUnitKey1": OrderUnitKey_생성()])
    result == expectResult

    where:
    desc            | cashbackResponse                                                                                                        | cashbackBackendResponse                                                                                           | expectResult
    "아이템 캐시백"       | CashbackResponseDataDto_생성()                                                                                            | [CashbackRewardBackendResponseDto_생성()]                                                                           | RewardCashbackPolicies_생성()
    "아이템,스마일페이 캐시백" | CashbackResponseDataDto_생성(cashbackInfo: [CashbackInfoDto_생성(), CashbackInfoDto_생성(cashbackCd: CashbackType.SmilePay)]) | [CashbackRewardBackendResponseDto_생성(), CashbackRewardBackendResponseDto_생성(cashbackCode: CashbackType.SmilePay)] | RewardCashbackPolicies_생성(cashbackPolicies: [RewardCashbackPolicy_생성(), RewardCashbackPolicy_생성(cashbackCd: CashbackType.SmilePay)], backendCashbackPolicies: [RewardBackendCashbackPolicy_생성(), RewardBackendCashbackPolicy_생성(cashbackCode: CashbackType.SmilePay)])
  }

  def "request 정보 중 결제금액이 정상적으로 변환되는지 확인한다"() {
    expect:
    def result = rewardGatewayImpl.toCashbackRewardRequestDto(Order_생성(), 결제, ["itemSnapshotKey1": ItemSnapshot_생성()], ["orderUnitKey1": OrderUnitKey_생성()])
    result == expectResult

    where:
    desc   | 결제                                                     | expectResult
    "기본"   | 스마일페이_Payment_생성()                                           | CashbackRequestDataDto_생성()
    "복합결제" | 스마일페이_Payment_생성(subPaymentMethods: [PaymentMethodSub_생성()]) | CashbackRequestDataDto_생성()
  }

  def "request 중 goods에 대한 매핑이 정상인지 확인한다"() {
    expect:
    def result = rewardGatewayImpl.buildGoods(주문, 상품, 키)
    result == expectResult

    where:
    desc    | 주문                                                                                  | 상품                                      | 키                                                                                                                     | expectResult
    "기본"    | Order_생성()                                                                          | ["itemSnapshotKey1": ItemSnapshot_생성()] | ["orderUnitKey1": OrderUnitKey_생성()]                                                                                  | [CashbackRewardGoodRequestDto_생성()]
    "여러 주문" | Order_생성(orderUnits: [OrderUnit_생성(), OrderUnit_생성(orderUnitKey: "orderUnitKey2")]) | ["itemSnapshotKey1": ItemSnapshot_생성()] | ["orderUnitKey1": OrderUnitKey_생성(), "orderUnitKey2": OrderUnitKey_생성(orderUnitKey: "orderUnitKey2", buyOrderNo: 2L)] | [CashbackRewardGoodRequestDto_생성(), CashbackRewardGoodRequestDto_생성(key: "2")]
  }
}
