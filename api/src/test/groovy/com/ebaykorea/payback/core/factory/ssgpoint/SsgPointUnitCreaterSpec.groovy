package com.ebaykorea.payback.core.factory.ssgpoint

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.core.domain.entity.order.KeyMap
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointGmarketState
import com.ebaykorea.payback.core.service.SsgPointStateDelegate
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.grocery.OrderGrocery.KeyMap_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.OrderUnitKey_생성
import static com.ebaykorea.payback.grocery.OrderGrocery.Order_생성
import static com.ebaykorea.payback.grocery.RewardGrocery.RewardSsgPointPolicy_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointGmarketState_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointUnit_준비상태_생성

class SsgPointUnitCreaterSpec extends Specification {
  def delegates = new SsgPointStateDelegate(Set.of(new SsgPointGmarketState()))
  def ssgPointUnitCreater = new SsgPointUnitCreater(delegates)

  def "SsgPointUnit 생성 확인"() {

    expect:
    def result = ssgPointUnitCreater.readyUnits(
        [1L: RewardSsgPointPolicy_생성(pointExpectSaveAmount: 1000L, isSsgPoint: true, policyDay: policyDay)],
        Order_생성(orderDate: TestConstant.SSGPOINT_ORDER_DATE),
        KeyMap_생성(orderUnitKeys: List.of(OrderUnitKey_생성(contrNo: 1000000000L))),
        SsgPointGmarketState_생성()
    )

    result == List.of(SsgPointUnit_준비상태_생성(isPolicy: true, scheduleDate: expectScheduleDate))

    where:
    desc         | policyDay | expectScheduleDate
    "기본 정책일자_+5" | null      | TestConstant.SSGPOINT_SCHEDULE_DATE
    "변경된 정책일자"   | 4        | Instant.parse("2023-04-15T13:00:00.00Z")
  }
}
