package com.ebaykorea.payback.core


import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto
import com.ebaykorea.payback.core.gateway.UserGateway
import com.ebaykorea.payback.core.repository.TossRewardRequestRepository
import com.ebaykorea.payback.core.repository.SmileCashEventRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.TossEventRewardResponseDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.SmileCashEventResult_생성
import static com.ebaykorea.payback.grocery.TossRewardGrocery.TossRewardRequestResult_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.TossEventRewardGrocery.TossRewardRequestDto_생성

class TossEventRewardApplicationServiceSpec extends Specification {
  def eventRewardRepository = Stub(TossRewardRequestRepository)
  def smileCashEventRepository = Stub(SmileCashEventRepository)
  def userGateway = Stub(UserGateway)

  def service = new TossEventRewardApplicationService(eventRewardRepository, smileCashEventRepository, userGateway)

  def "토스 사후적립 적립 요청 처리 테스트"() {
    setup:
    eventRewardRepository.find(_ as TossRewardRequestDto) >> Optional.ofNullable(eventReward결과)
    eventRewardRepository.save(_ as TossRewardRequestDto) >> 1L
    eventRewardRepository.saveStatus(_ as Long, _ as TossRewardRequestStatusType) >> {}
    smileCashEventRepository.save(_ as SmileCashEvent) >> Optional.ofNullable(회원적립결과)
    userGateway.getUserId(_ as String) >> "userId"
    smileCashEventRepository.find(_ as SmileCashEvent) >> Optional.ofNullable(SmileCashEventResult_생성())

    expect:
    def result = service.saveEventReward(TossRewardRequestDto_생성())
    result == expectResult

    where:
    ________________________________________________
    desc                | expectResult
    "중복요청"              | TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "ALREADY_PROCESSED")
    "적립실패_회원적립 결과가 실패"  | TossEventRewardResponseDto_생성(resultCode: "FAILED")
    "적립실패_회원적립 결과가 없을때" | TossEventRewardResponseDto_생성(resultCode: "FAILED")
    "적립성공"              | TossEventRewardResponseDto_생성(smilePayNo: "2", resultCode: "SUCCESS")
    ________________________________________________
    eventReward결과                | 회원적립결과
    TossRewardRequestResult_생성() | EventRewardResultDto_생성()
    null                         | EventRewardResultDto_생성()
    null                         | null
    null                         | EventRewardResultDto_생성(savingNo: 2L)
  }

  def "이벤트 리워드 조회 테스트"() {
    setup:
    eventRewardRepository.find(_ as TossRewardRequestDto) >> Optional.ofNullable(eventReward결과)
    userGateway.getUserId(_ as String) >> "userId"
    smileCashEventRepository.find(_ as SmileCashEvent) >> Optional.ofNullable(smileCashEvent결과)

    expect:
    def result = service.getEventReward(TossRewardRequestDto_생성())
    result == expectResult

    where:
    ________________________________________________
    desc                           | expectResult
    "이벤트 리워드 요청이 없었던 경우"           | TossEventRewardResponseDto_생성(resultCode: "NOT_FOUND")
    "스마일캐시 적립 요청이 없었던 경우"          | TossEventRewardResponseDto_생성(resultCode: "FAILED")
    "스마일캐시 적립 요청은 되었지만 적립 대기중인 경우" | TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "IN_PROGRESS")
    "스마일캐시 적립 요청은 되었지만 적립 실패된 경우"  | TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "FAILED", resultMessage: "T999")
    "스마일캐시 적립 성공된 경우"              | TossEventRewardResponseDto_생성(smilePayNo: "1", resultCode: "SUCCESS")
    ________________________________________________
    eventReward결과                | smileCashEvent결과
    null                         | null
    TossRewardRequestResult_생성() | null
    TossRewardRequestResult_생성() | SmileCashEventResult_생성()
    TossRewardRequestResult_생성() | SmileCashEventResult_생성(failed: true, returnCode: "T999")
    TossRewardRequestResult_생성() | SmileCashEventResult_생성(saved: true)

  }
}