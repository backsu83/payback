package com.ebaykorea.payback.core.service

import com.ebaykorea.payback.core.domain.constant.OrderSiteType
import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointAuctionState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointGmarketState
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState
import com.ebaykorea.payback.core.repository.SsgPointRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SaveSsgPointGrocery.SaveSsgPointRequestDto_생성
import static com.ebaykorea.payback.grocery.SaveSsgPointGrocery.SsgPointTargetResponseDto_생성

class SsgPointServiceSpec extends Specification {
  def states = Set.of(new SsgPointGmarketState(), new SsgPointAuctionState())
  def ssgPointStateDelegate = new SsgPointStateDelegate(states)
  def ssgPointRepository = Mock(SsgPointRepository)

  def ssgPointService = new SsgPointService(ssgPointStateDelegate, ssgPointRepository)

  def "earnPoint 정상처리 확인"() {
    setup:
    ssgPointRepository.findByKey(_ as Long, _ as String, _ as String, _ as String) >> 조회결과

    when:
    def result = ssgPointService.earnPoint(request)


    then:
    result == 결과
    저장호출횟수 * ssgPointRepository.save(_ as SsgPoint) >> {[결과]}

    where:
    _________________________________________________
    desc | request | 결과 | _
    "중복 호출 된 경우" | SaveSsgPointRequestDto_생성() | SsgPointTargetResponseDto_생성() | _
    "일반호출" | SaveSsgPointRequestDto_생성() | SsgPointTargetResponseDto_생성() | _
    _________________________________________________
    조회결과 | 저장호출횟수 | _
    Optional.of(SsgPointTargetResponseDto_생성()) | 0 | _
    Optional.empty() | 1 | _
  }
}
