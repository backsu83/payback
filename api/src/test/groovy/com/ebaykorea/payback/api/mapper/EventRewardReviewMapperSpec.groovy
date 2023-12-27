package com.ebaykorea.payback.api.mapper

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.ReviewRewardGrocery.ReviewRewardRequestDto_생성

class EventRewardReviewMapperSpec extends Specification {
  def mapper = Mappers.getMapper(EventRewardReviewMapper.class)

  def "상품평 리워드 dto 변환 확인"() {
    expect:
    def result = mapper.map(request, eventType)
    result == expectResult

    where:
    desc           | request                                                               | eventType               | expectResult
    "코어 일반 상품평"          | ReviewRewardRequestDto_생성()                                           | EventType.Review        | EventRewardRequestDto_생성(requestNo: 1L, saveAmount: 10, eventType: EventType.Review, eventNo: 100)
    "렌터카 프리미엄 상품평" | ReviewRewardRequestDto_생성(referenceType: ReviewReferenceType.RentalCar) | EventType.ReviewPremium | EventRewardRequestDto_생성(requestNo: 1L, saveAmount: 10, eventType: EventType.ReviewPremium, eventNo: 101)
  }
}
