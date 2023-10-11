package com.ebaykorea.payback.core

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType
import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto
import com.ebaykorea.payback.core.gateway.UserGateway
import com.ebaykorea.payback.core.repository.EventRewardRepository
import com.ebaykorea.payback.core.repository.SmileCashEventRepository
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.EventRewardDtoGrocery.EventRewardResponseDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.MemberEventRewardResultDto_생성

class EventRewardApplicationServiceSpec extends Specification {
  def eventRewardRepository = Stub(EventRewardRepository)
  def smileCashEventRepository = Stub(SmileCashEventRepository)
  def userGateway = Stub(UserGateway)

  def service = new EventRewardApplicationService(eventRewardRepository, smileCashEventRepository, userGateway)

  def "이벤트 리워드 적립 요청 처리 테스트"() {
    setup:
    eventRewardRepository.alreadySaved(_ as String, _ as EventType) >> 중복처리여부
    eventRewardRepository.save(_ as EventRewardRequestDto) >> 1L
    eventRewardRepository.saveStatus(_ as String, _ as EventType, _ as EventRequestStatusType) >> {}
    smileCashEventRepository.save(_ as String, _ as MemberEventRewardRequestDto) >> Optional.ofNullable(회원적립결과)
    userGateway.getUserId(_ as String) >> "userId"

    expect:
    def result = service.saveEventReward(request)
    result == expectResult

    where:
    ________________________________________________
    desc | request | expectResult
    "중복요청" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "ALREADY_PROCESSED")
    "적립실패_회원적립 결과가 실패" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "FAILED")
    "적립실패_회원적립 결과가 없을때" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(resultCode: "FAILED")
    "적립성공" | EventRewardRequestDto_생성() | EventRewardResponseDto_생성(smilePayNo: "2", resultCode: "SUCCESS")
    ________________________________________________
    중복처리여부 | 회원적립결과
    true | MemberEventRewardResultDto_생성()
    false | MemberEventRewardResultDto_생성()
    false | null
    false | MemberEventRewardResultDto_생성(smilePayNo: 2L)


  }
}
