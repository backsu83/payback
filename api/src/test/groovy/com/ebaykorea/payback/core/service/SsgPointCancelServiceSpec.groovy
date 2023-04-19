package com.ebaykorea.payback.core.service

import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointAuctionState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointGmarketState
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointOrderNoDto
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey
import com.ebaykorea.payback.core.factory.ssgpoint.SsgPointCreater
import com.ebaykorea.payback.core.factory.ssgpoint.SsgPointUnitCreater
import com.ebaykorea.payback.core.repository.SsgPointRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SsgPointGrocery.CancelSsgPointRequestDto_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointTarget_생성

class SsgPointCancelServiceSpec extends Specification {
  def states = Set.of(new SsgPointGmarketState(), new SsgPointAuctionState())
  def ssgPointStateDelegate = new SsgPointStateDelegate(states)
  def ssgPointUnitCreater = new SsgPointUnitCreater(ssgPointStateDelegate)
  def ssgPointCreater = new SsgPointCreater(ssgPointUnitCreater)
  def ssgPointRepository = Mock(SsgPointRepository)
  def ssgPointCancelService = new SsgPointCancelService(ssgPointRepository, ssgPointCreater)

  def "cancelPoint 정상 처리 확인"() {
    setup:
    ssgPointRepository.findByKey(_ as SsgPointRequestKey) >> Optional.ofNullable(키조회결과)
    ssgPointRepository.findAllByOrderNoAndSiteType(_ as Long, _ as String, _ as OrderSiteType) >> 조회결과

    when:
    def result = ssgPointCancelService.cancelPoint(1L, request)

    then:
    result == 결과
    취소호출횟수 * ssgPointRepository.cancel(_ as SsgPoint) >> { [결과] }
    예외저장호출횟수 * ssgPointRepository.saveExceptOrderNo(_ as SsgPointOrderNoDto) >> {}
    업데이트호출횟수 * ssgPointRepository.setPointStatus(_ as SsgPoint) >> {}

    where:
    _________________________________________________
    desc | request | 결과 | _
    "중복 호출 된 경우" | CancelSsgPointRequestDto_생성() | SsgPointTarget_생성(tradeType: "C") | _
    "적립된 데이터가 없는 경우" | CancelSsgPointRequestDto_생성() | null | _
    "적립건 취소 처리" | CancelSsgPointRequestDto_생성() | SsgPointTarget_생성() | _
    "대기건 보류 처리" | CancelSsgPointRequestDto_생성() | SsgPointTarget_생성() | _
    _________________________________________________
    키조회결과 | 조회결과 | 취소호출횟수 | 예외저장호출횟수 | 업데이트호출횟수 | _
    SsgPointTarget_생성(tradeType: "C") | [키조회결과] | 0 | 0 | 0 | _
    null | [] | 0 | 1 | 0 | _
    SsgPointTarget_생성(pointStatus: "SS") | [키조회결과] | 1 | 0 | 0 | _
    SsgPointTarget_생성() | [키조회결과] | 0 | 0 | 1 | _
  }
}
