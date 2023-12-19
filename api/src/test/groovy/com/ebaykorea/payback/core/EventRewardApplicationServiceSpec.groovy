package com.ebaykorea.payback.core

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType
import com.ebaykorea.payback.core.dto.event.CashEventRewardRequest
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto
import com.ebaykorea.payback.core.gateway.UserGateway
import com.ebaykorea.payback.core.repository.EventRewardRepository
import com.ebaykorea.payback.core.repository.SmileCashEventRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.CashEventRewardDtoGrocery.CashEventRewardResult_생성
import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.EventRewardResponseDto_생성
import static com.ebaykorea.payback.grocery.EventRewardGrocery.EventReward_생성
import static com.ebaykorea.payback.grocery.EventRewardGrocery.SmileCashEvent_생성

class EventRewardApplicationServiceSpec extends Specification {
  def eventRewardRepository = Stub(EventRewardRepository)
  def smileCashEventRepository = Stub(SmileCashEventRepository)
  def userGateway = Stub(UserGateway)

  def service = new EventRewardApplicationService(eventRewardRepository, smileCashEventRepository, userGateway)

  def "이벤트 리워드 적립 요청 처리 테스트"() {
    setup:
    eventRewardRepository.findEventReward(_ as EventRewardRequestDto) >> Optional.ofNullable(eventReward결과)
    eventRewardRepository.save(_ as EventRewardRequestDto) >> 1L
    eventRewardRepository.saveStatus(_ as Long, _ as EventRequestStatusType) >> {}
    smileCashEventRepository.save(_ as CashEventRewardRequest) >> Optional.ofNullable(회원적립결과)
    userGateway.getUserId(_ as String) >> "userId"
    smileCashEventRepository.find(_ as String, _ as CashEventRewardRequest) >> Optional.ofNullable(SmileCashEvent_생성())

    expect:
    def result = service.saveEventReward(request)
    result == expectResult

    where:
    ________________________________________________
    desc | request | expectResult
    "중복요청" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "1", resultCode: "ALREADY_PROCESSED")
    "적립실패_회원적립 결과가 실패" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "FAILED")
    "적립실패_회원적립 결과가 없을때" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "FAILED")
    "적립성공" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "2", resultCode: "SUCCESS")
    ________________________________________________
    eventReward결과 | 회원적립결과
    EventReward_생성() | CashEventRewardResult_생성()
    null | CashEventRewardResult_생성()
    null | null
    null | CashEventRewardResult_생성(smilePayNo: 2L)
  }

  def "이벤트 리워드 조회 테스트"() {
    setup:
    eventRewardRepository.findEventReward(_ as EventRewardRequestDto) >> Optional.ofNullable(eventReward결과)
    userGateway.getUserId(_ as String) >> "userId"
    smileCashEventRepository.find(_ as String, _ as CashEventRewardRequest) >> Optional.ofNullable(smileCashEvent결과)

    expect:
    def result = service.getEventReward(request)
    result == expectResult

    where:
    ________________________________________________
    desc | request | expectResult
    "이벤트 리워드 요청이 없었던 경우" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "NOT_FOUND")
    "스마일캐시 적립 요청이 없었던 경우" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "FAILED")
    "스마일캐시 적립 요청은 되었지만 적립 대기중인 경우" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "1", resultCode: "IN_PROGRESS")
    "스마일캐시 적립 요청은 되었지만 적립 실패된 경우" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "1", resultCode: "FAILED", resultMessage: "T999")
    "스마일캐시 적립 성공된 경우" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "1", resultCode: "SUCCESS")
    ________________________________________________
    eventReward결과 | smileCashEvent결과
    null | null
    EventReward_생성() | null
    EventReward_생성() | SmileCashEvent_생성()
    EventReward_생성() | SmileCashEvent_생성(failed: true, returnCode: "T999")
    EventReward_생성() | SmileCashEvent_생성(saved: true)

  }
}
