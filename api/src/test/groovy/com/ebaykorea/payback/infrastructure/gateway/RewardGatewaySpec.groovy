package com.ebaykorea.payback.infrastructure.gateway

import com.ebaykorea.payback.core.domain.constant.CashbackType
import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardGoodRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseResponse
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseReturn
import com.ebaykorea.payback.infrastructure.mapper.RewardGatewayMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.OrderGrocery.ItemSnapshot_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.Order_생성
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
    def result = rewardGatewayImpl.findCashbackPolicies(Order_생성(), ["itemSnapshotKey1": ItemSnapshot_생성()])
    result == expectResult

    where:
    desc            | cashbackResponse                                                                                                        | cashbackBackendResponse                                                                                           | expectResult
    "아이템 캐시백"       | CashbackResponseDataDto_생성()                                                                                            | [CashbackRewardBackendResponseDto_생성()]                                                                           | RewardCashbackPolicies_생성()
    "아이템,스마일페이 캐시백" | CashbackResponseDataDto_생성(cashbackInfo: [CashbackInfoDto_생성(), CashbackInfoDto_생성(cashbackCd: CashbackType.SmilePay)]) | [CashbackRewardBackendResponseDto_생성(), CashbackRewardBackendResponseDto_생성(cashbackCode: CashbackType.SmilePay)] | RewardCashbackPolicies_생성(cashbackPolicies: [RewardCashbackPolicy_생성(), RewardCashbackPolicy_생성(cashbackCd: CashbackType.SmilePay)], backendCashbackPolicies: [RewardBackendCashbackPolicy_생성(), RewardBackendCashbackPolicy_생성(cashbackCode: CashbackType.SmilePay)])
  }

  def "request에 대한 매핑이 정상인지 확인한다"() {
    expect:
    def result = rewardGatewayImpl.buildGoods(주문, 상품)
    result == expectResult

    where:
    desc | 주문         | 상품                                      | expectResult
    "기본" | Order_생성() | ["itemSnapshotKey1": ItemSnapshot_생성()] | [CashbackRewardGoodRequestDto_생성()]
  }
}
