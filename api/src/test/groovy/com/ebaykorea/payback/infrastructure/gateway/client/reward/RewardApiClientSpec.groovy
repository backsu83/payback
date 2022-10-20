package com.ebaykorea.payback.infrastructure.gateway.client.reward

import com.ebaykorea.payback.infrastructure.gateway.client.reward.RewardApiClient
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.CashbackRewardRequestDto
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseResponse
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardBaseReturn
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackRequestDataDto_생성
import static com.ebaykorea.payback.grocery.RewardApiGrocery.CashbackResponseDataDto_생성

class RewardApiClientSpec extends Specification {
  def rewardApiClient = Stub(RewardApiClient)

  def "CashbackReward 테스트"() {
    setup:
    rewardApiClient.getCashbackReward(_ as CashbackRewardRequestDto) >> response

    expect:
    def result = rewardApiClient.getCashbackReward(request)
    result == response

    where:
    desc | request                     | response
    "캐시백 api 테스트"   | CashbackRequestDataDto_생성() | RewardBaseResponse.builder()
                                                              .returnBase(RewardBaseReturn.builder().returnCode("0000").build())
                                                              .result(CashbackResponseDataDto_생성()).build()
  }
}
