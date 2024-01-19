package com.ebaykorea.payback.infrastructure.query.mapper

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.EventType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성
import static com.ebaykorea.payback.grocery.ReviewRewardQueryResultGrocery.ReviewRewardQueryResult_생성

class ReviewRewardQueryMapperSpec extends Specification {
  def mapper = Mappers.getMapper(ReviewRewardQueryMapper)

  def "reviewRewardQueryMapper가 SmileCashEventEntity 데이터 변환"() {
    expect:
    def result = mapper.map(request , eventType)

    result == expectResult

    where:
    desc | eventType | request | expectResult
    "지마켓 리뷰 조회" | EventType.Review | SmileCashEventEntity_생성(status: 50, requestMoney: 10L) | ReviewRewardQueryResult_생성(reviewType: EventType.Review, save: true , saveAmount: 10L)
    "지마켓 리뷰(프리미엄) 조회" | EventType.ReviewPremium | SmileCashEventEntity_생성(status: 50, requestMoney: 10L) | ReviewRewardQueryResult_생성(reviewType: EventType.ReviewPremium, save: true , saveAmount: 10L)

  }

  def "reviewRewardQueryMapper가 SmileCashSaveQueueEntity 데이터 변환"() {
    expect:
    def result = mapper.map(request)

    result == expectResult

    where:
    desc | request | expectResult
    "옥션 리뷰 조회" |  SmileCashSaveQueueEntity_생성(reasonCode: "RM04Y", saveStatus: 1, saveAmount: 10L) | ReviewRewardQueryResult_생성(reviewType: EventType.Review, save: true , saveAmount: 10L)
    "옥션 리뷰(프리미엄) 조회" |  SmileCashSaveQueueEntity_생성(reasonCode: "RM05Y", saveStatus: 1, saveAmount: 10L) | ReviewRewardQueryResult_생성(reviewType: EventType.ReviewPremium, save: true , saveAmount: 10L)

  }
}
