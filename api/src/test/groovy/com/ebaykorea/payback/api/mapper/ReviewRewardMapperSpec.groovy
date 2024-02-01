package com.ebaykorea.payback.api.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.ReviewRewardGrocery.ReviewRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.ReviewReward_생성

class ReviewRewardMapperSpec extends Specification {
  def mapper = Mappers.getMapper(ReviewRewardMapper.class)

  def "ReviewRewardRequestDto -> ReviewReward 매핑 확인"() {
    expect:
    def result = mapper.map(request, eventType, promotionType)
    result == expectResult

    where:
    _________________________________________________
    desc | request | expectResult
    "코어 일반 상품평"       | ReviewRewardRequestDto_생성()  | ReviewReward_생성(saveAmount: 10, reviewPromotionType: ReviewPromotionType.Normal)
    "렌터카 프리미엄 상품평" | ReviewRewardRequestDto_생성(referenceType: ReviewReferenceType.Tour) | ReviewReward_생성(saveAmount: 10, eventType: EventType.ReviewPremium, referenceType: ReviewReferenceType.Tour, reviewPromotionType: ReviewPromotionType.Premium, defaultComments: "여행 후기 추가 적립")
    _________________________________________________
    eventType | promotionType | _
    EventType.Review | ReviewPromotionType.Normal | _
    EventType.ReviewPremium | ReviewPromotionType.Premium | _
  }
}
