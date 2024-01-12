package com.ebaykorea.payback.core.domain.entity.event

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.core.exception.PaybackExceptionCode
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.EventReward_생성

class EventRewardSpec extends Specification {
  def "이벤트 리워드는 허용하는 이벤트 타입으로만 생성 가능"() {
    when:
    EventReward_생성(eventType: EventType.Review)

    then:
    def ex1 = thrown(PaybackException)
    ex1.code == PaybackExceptionCode.DOMAIN_ENTITY_001

    when:
    EventReward_생성(eventType: EventType.ReviewPremium)

    then:
    def ex2 = thrown(PaybackException)
    ex2.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }
}
