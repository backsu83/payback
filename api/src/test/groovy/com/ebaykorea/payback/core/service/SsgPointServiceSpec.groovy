package com.ebaykorea.payback.core.service

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPoint
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointStateImpl
import com.ebaykorea.payback.core.dto.ssgpoint.SsgPointRequestKey
import com.ebaykorea.payback.core.repository.SsgPointRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.SsgPointGrocery.SaveSsgPointRequestDto_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.SsgPointTarget_생성

class SsgPointServiceSpec extends Specification {
  def ssgPointRepository = Mock(SsgPointRepository)
  def ssgPointState = new SsgPointStateImpl()
  def ssgPointService = new SsgPointService(ssgPointRepository, ssgPointState)

  def "earnPoint 정상처리 확인"() {
    setup:
    ssgPointRepository.findByKey(_ as SsgPointRequestKey) >> 조회결과

    when:
    def result = ssgPointService.earnPoint(request)


    then:
    result == 결과
    저장호출횟수 * ssgPointRepository.save(_ as SsgPoint) >> {[결과]}

    where:
    _________________________________________________
    desc | request | 결과 | _
    "중복 호출 된 경우" | SaveSsgPointRequestDto_생성() | SsgPointTarget_생성() | _
    "일반호출" | SaveSsgPointRequestDto_생성() | SsgPointTarget_생성() | _
    _________________________________________________
    조회결과 | 저장호출횟수 | _
    Optional.of(SsgPointTarget_생성()) | 0 | _
    Optional.empty() | 1 | _
  }
}
