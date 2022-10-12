package com.ebaykorea.payback.infrastructure.gateway.client

import com.ebaykorea.payback.infrastructure.gateway.client.dto.CashbackRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.dto.RewardBaseResponse
import com.ebaykorea.payback.infrastructure.gateway.client.dto.RewardBaseReturn
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackRequestDataDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackResponseDataDto_생성

class RewardApiClientSpec extends Specification {
  def rewardApiClient = Stub(RewardApiClient)

  def "CashbackReward 테스트"() {
    setup:
    rewardApiClient.cashbackReward(_ as CashbackRequestDto) >> response

    expect:
    def result = rewardApiClient.cashbackReward(request)
    result == response

    where:
    desc | request                     | response
    "캐시백 api 테스트"   | CashbackRequestDataDto_생성() | new RewardBaseResponse(RewardBaseReturn.builder().returnCode("0000").build(), CashbackResponseDataDto_생성())
  }
}
