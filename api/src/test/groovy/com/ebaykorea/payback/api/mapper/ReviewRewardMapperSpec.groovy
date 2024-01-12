package com.ebaykorea.payback.api.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.ReviewRewardGrocery.ReviewRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.ReviewReward_생성

class ReviewRewardMapperSpec extends Specification {
  def mapper = Mappers.getMapper(ReviewRewardMapper.class)

  def "ReviewRewardRequestDto -> ReviewReward 매핑 확인"() {
    expect:
    def result = mapper.map(request, eventType)
    result == expectResult

    where:
    desc           | request                                                               | eventType               | expectResult
    "코어 일반 상품평"          | ReviewRewardRequestDto_생성()                                           | EventType.Review        | ReviewReward_생성(saveAmount: 10)
    "렌터카 프리미엄 상품평" | ReviewRewardRequestDto_생성(referenceType: ReviewReferenceType.Tour) | EventType.ReviewPremium | ReviewReward_생성(saveAmount: 10, eventType: EventType.ReviewPremium, referenceType: ReviewReferenceType.Tour)
  }
}
