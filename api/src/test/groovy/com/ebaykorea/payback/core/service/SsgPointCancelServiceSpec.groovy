package com.ebaykorea.payback.core.service

import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointAuctionState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointGmarketState
import com.ebaykorea.payback.core.dto.SsgPointOrderNoDto
import com.ebaykorea.payback.core.repository.SsgPointRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SaveSsgPointGrocery.CancelSsgPointRequestDto_생성
import static com.ebaykorea.payback.grocery.SaveSsgPointGrocery.SsgPointTargetResponseDto_생성

class SsgPointCancelServiceSpec extends Specification {
  def states = Set.of(new SsgPointGmarketState(), new SsgPointAuctionState())
  def ssgPointStateDelegate = new SsgPointStateDelegate(states)
  def ssgPointRepository = Mock(SsgPointRepository)
  def ssgPointCancelService = new SsgPointCancelService(ssgPointStateDelegate, ssgPointRepository)

  def "cancelPoint 정상 처리 확인"() {
    setup:
    ssgPointRepository.findByKey(_ as Long, _ as String, _ as String, _ as String) >> Optional.ofNullable(키조회결과)
    ssgPointRepository.findAllByOrderNoAndSiteType(_ as Long, _ as String, _ as OrderSiteType) >> 조회결과

    when:
    def result = ssgPointCancelService.cancelPoint(1L, request)

    then:
    result == 결과
    저장호출횟수 * ssgPointRepository.save(_ as SsgPoint) >> { [결과] }
    예외저장호출횟수 * ssgPointRepository.setCancelOrderNoNoneSave(_ as SsgPointOrderNoDto) >> {}
    업데이트호출횟수 * ssgPointRepository.updatePointStatus(_ as SsgPoint) >> {}

    where:
    _________________________________________________
    desc | request | 결과 | _
    "중복 호출 된 경우" | CancelSsgPointRequestDto_생성() | SsgPointTargetResponseDto_생성(tradeType: "C") | _
    "적립된 데이터가 없는 경우" | CancelSsgPointRequestDto_생성() | null | _
    "적립건 취소 처리" | CancelSsgPointRequestDto_생성() | SsgPointTargetResponseDto_생성() | _
    "대기건 보류 처리" | CancelSsgPointRequestDto_생성() | SsgPointTargetResponseDto_생성() | _
    _________________________________________________
    키조회결과 | 조회결과 | 저장호출횟수 | 예외저장호출횟수 | 업데이트호출횟수 | _
    SsgPointTargetResponseDto_생성(tradeType: "C") | [키조회결과] | 0 | 0 | 0 | _
    null | [] | 0 | 1 | 0 | _
    SsgPointTargetResponseDto_생성(pointStatus: "SS") | [키조회결과] | 1 | 0 | 0 | _
    SsgPointTargetResponseDto_생성() | [키조회결과] | 0 | 0 | 1 | _
  }
}
