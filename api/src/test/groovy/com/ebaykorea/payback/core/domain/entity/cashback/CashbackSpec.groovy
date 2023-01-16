package com.ebaykorea.payback.core.domain.entity.cashback

import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.core.exception.PaybackExceptionCode
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.CashbackUnitGrocery.ItemCashback_생성
import static com.ebaykorea.payback.grocery.PayCashbackGrocery.Cashback_생성

class CashbackSpec extends Specification {
  def "하나의 Cashback 은 동일한 캐시백 타입의 cashbackUnit을 갖을 수 없다"() {
    when:
    Cashback_생성(cashbackUnits: [ItemCashback_생성(),ItemCashback_생성()])

    then:
    def e = thrown(PaybackException)
    e.code == PaybackExceptionCode.DOMAIN_ENTITY_001
  }

  def "서로 다른 Cashback은 동일한 캐시백 타입의 cashbackUnit을 갖을 수 있다"() {
    when:
    List.of(
        Cashback_생성(orderNo: 1L, cashbackUnits: [ItemCashback_생성()]),
        Cashback_생성(orderNo: 2L, cashbackUnits: [ItemCashback_생성()])
    )
    
    then:
    noExceptionThrown()
  }
}
