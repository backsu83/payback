package com.ebaykorea.payback.core.domain.entity.event

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.core.exception.PaybackExceptionCode
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.EventPlatform_생성

class EventRewardSpec extends Specification {
  def "이벤트 리워드는 허용하는 이벤트 타입으로만 생성 가능"() {
    when:
    EventPlatform_생성(eventType: EventType.Review)

    then:
    def ex1 = thrown(PaybackException)
    ex1.code == PaybackExceptionCode.DOMAIN_ENTITY_001

    when:
    EventPlatform_생성(eventType: EventType.ReviewPremium)

    then:
    def ex2 = thrown(PaybackException)
    ex2.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }

  def "이벤트 리워드는 이미 지난 유효기간은 입력 불가"() {
    when:
    EventPlatform_생성(expirationDate: TestConstant.ORDER_DATE)

    then:
    def ex1 = thrown(PaybackException)
    ex1.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }

  def "이벤트 리워드는 예산 번호가 있어야 함"() {
    when:
    EventPlatform_생성(budgetNo: 0L)

    then:
    def ex1 = thrown(PaybackException)
    ex1.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }
}
