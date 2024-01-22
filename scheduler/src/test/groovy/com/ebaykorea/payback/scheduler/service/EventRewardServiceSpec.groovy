package com.ebaykorea.payback.scheduler.service

import com.ebaykorea.payback.scheduler.client.PaybackApiClient
import com.ebaykorea.payback.scheduler.client.QuiltApiClient
import com.ebaykorea.payback.scheduler.client.dto.payback.EventRewardRequestDto
import com.ebaykorea.payback.scheduler.model.constant.EventRequestStatusType
import com.ebaykorea.payback.scheduler.repository.opayreward.EventRewardRepositoryCustom
import com.ebaykorea.payback.scheduler.repository.opayreward.EventRewardRequestStatusRepository
import com.ebaykorea.payback.scheduler.repository.opayreward.entity.event.EventRewardRequestStatusEntity
import com.ebaykorea.payback.scheduler.service.EventRewardService
import spock.lang.Specification

import static com.ebaykorea.payback.scheduler.grocery.EventRewardRequestEntityGrocery.EventRewardRequestEntity_생성
import static com.ebaykorea.payback.scheduler.grocery.PaybackApiGrocery.CommonResponse_생성
import static com.ebaykorea.payback.scheduler.grocery.QuiltApiGrocery.QuiltBaseResponse_생성

class EventRewardServiceSpec extends Specification {
  def eventRewardRepositoryCustom = Stub(EventRewardRepositoryCustom)
  def statusRepository = Mock(EventRewardRequestStatusRepository)
  def quiltApi = Stub(QuiltApiClient)
  def paybackApiClient = Stub(PaybackApiClient)

  def service = new EventRewardService(eventRewardRepositoryCustom, statusRepository, quiltApi, paybackApiClient)

  def "eventReward 미처리건에 대한 대응 확인"() {
    setup:
    eventRewardRepositoryCustom.findNotRequestedRequests(_ as String, _ as String, _ as String) >> 미처리대상
    quiltApi.findUserId(_ as String) >> Optional.ofNullable(QuiltBaseResponse_생성())
    paybackApiClient.saveEventRewardByMember(_ as EventRewardRequestDto) >> Optional.ofNullable(CommonResponse_생성())

    when:
    service.run("", "", "")

    then:
    저장호출 * statusRepository.save(_ as EventRewardRequestStatusEntity) >> {}

    where:
    desc                    | 미처리대상                                                                                   | 저장호출
    "실패 발생시 상태 저장"  | [EventRewardRequestEntity_생성()]                                                         | 1
    "상태 존재 시 상태 저장하지 않음" | [EventRewardRequestEntity_생성(eventRequestStatus: EventRequestStatusType.RequestFailed)] | 0
  }
}
