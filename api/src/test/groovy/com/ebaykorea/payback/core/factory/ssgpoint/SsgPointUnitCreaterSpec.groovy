package com.ebaykorea.payback.core.factory.ssgpoint

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointStateImpl
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.grocery.OrderGrocery.*
import static com.ebaykorea.payback.grocery.PaymentGrocery.스마일페이_Payment_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.카드_Payment_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardSsgPointPolicy_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointUnit_준비상태_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointState_생성

class SsgPointUnitCreaterSpec extends Specification {
  def ssgPointUnitCreater = new SsgPointUnitCreater()

  def "SsgPointUnit 생성시 날짜정보가 올바르게 생성되는지 확인"() {

    expect:
    def result = ssgPointUnitCreater.readyUnits(
        [1L: RewardSsgPointPolicy_생성(pointExpectSaveAmount: 1000L, isSsgPoint: true, policyDay: policyDay)],
        Order_생성(orderDate: TestConstant.SSGPOINT_ORDER_DATE),
        KeyMap_생성(orderUnitKeys: List.of(OrderUnitKey_생성(contrNo: 1000000000L))),
        스마일페이_Payment_생성(),
        SsgPointState_생성()
    )

    result == List.of(SsgPointUnit_준비상태_생성(isPolicy: true, scheduleDate: expectScheduleDate))

    where:
    desc         | policyDay | expectScheduleDate
    "기본 정책일자_+5" | null      | TestConstant.SSGPOINT_SCHEDULE_DATE
    "변경된 정책일자"   | 4        | Instant.parse("2023-04-15T13:00:00.00Z")
  }

  def "결제상태에 따라 SsgPointUnit 생성여부 확인"() {
    expect:
    def result = ssgPointUnitCreater.readyUnits(
        [1L: RewardSsgPointPolicy_생성(pointExpectSaveAmount: 1000L, isSsgPoint: true, policyDay: 5)],
        Order_생성(orderDate: TestConstant.SSGPOINT_ORDER_DATE),
        KeyMap_생성(orderUnitKeys: List.of(OrderUnitKey_생성(contrNo: 1000000000L))),
        결제정보,
        SsgPointState_생성()
    )

    result == 결과

    where:
    desc         |  결제정보 | 결과
    "스마일페이 일때" | 스마일페이_Payment_생성() | List.of(SsgPointUnit_준비상태_생성(isPolicy: true))
    "스마일페이 아닐때" | 카드_Payment_생성() | []
  }
}
