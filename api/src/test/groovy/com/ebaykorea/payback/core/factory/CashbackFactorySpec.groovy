package com.ebaykorea.payback.core.factory

import spock.lang.Specification

import static com.ebaykorea.payback.grocery.MemberGrocery.회원_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.ItemSnapshot_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.ItemSnapshots_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.KeyMap_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.Order_생성
import static com.ebaykorea.payback.grocery.PayCashbackGrocery.ItemCashback_생성
import static com.ebaykorea.payback.grocery.PayCashbackGrocery.SellerCashback_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.스마일페이_Payment_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardCashbackPolicies_생성

class CashbackFactorySpec extends Specification {
  def cashbackFactory = new CashbackFactory()

  //TODO: case 추가 예정
  def "Cashback이 정상적으로 생성되는지 확인한다"() {
    expect:
    def result = cashbackFactory.createCashbacks(키맵, 주문, 회원, 결제, 상품, 리워드정책)
    result.collect { [it.orderNo, it.itemNo, it.type, it.shopType, it.amount, it.basisAmount, it.useEnableDate] }
        == 결과.collect { [it.orderNo, it.itemNo, it.type, it.shopType, it.amount, it.basisAmount, it.useEnableDate] }

    where:
    desc | 결과                                                       | 키맵          | 주문         | 회원      | 결제                 | 상품                                                   | 리워드정책
    "기본" | [SellerCashback_생성(), ItemCashback_생성(isSmilePay: true)] | KeyMap_생성() | Order_생성() | 회원_생성() | 스마일페이_Payment_생성() | ItemSnapshots_생성(itemSnapshots: [ItemSnapshot_생성()]) | RewardCashbackPolicies_생성()
  }

}
