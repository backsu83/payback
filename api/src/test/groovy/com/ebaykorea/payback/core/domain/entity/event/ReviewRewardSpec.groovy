package com.ebaykorea.payback.core.domain.entity.event

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.core.exception.PaybackExceptionCode
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.Review_생성

class ReviewRewardSpec extends Specification {
  def "상품평 리워드는 허용하는 상품평 타입으로만 생성 가능"() {
    when:
    Review_생성(eventType: EventType.DailyCheckIn)

    then:
    def ex1 = thrown(PaybackException)
    ex1.code == PaybackExceptionCode.DOMAIN_ENTITY_001

    when:
    Review_생성(eventType: EventType.Toss)

    then:
    def ex2 = thrown(PaybackException)
    ex2.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }
}
