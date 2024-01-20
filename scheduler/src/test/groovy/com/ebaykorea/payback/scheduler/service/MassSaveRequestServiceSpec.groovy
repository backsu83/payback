package com.ebaykorea.payback.scheduler.service

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.scheduler.service.member.MemberService
import org.mapstruct.factory.Mappers
import spock.lang.Ignore
import spock.lang.Specification

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashResponseDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveQueueEntity_생성

class MassSaveRequestServiceSpec extends Specification {
  def smileCashSaveQueueRepository = Mock(SmileCashSaveQueueRepository)
  def memberService = Mock(MemberService)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def mapper = Mappers.getMapper(MassSaveRequestMapper)

  def service = new MassSaveRequestService(smileCashSaveQueueRepository, memberService, smileCashApiClient, mapper)

  def "MassSaveRequestService 동작 확인"() {
    when:
    service.run(1,5)
    Thread.sleep(100)

    then:
    1 * smileCashSaveQueueRepository.findTargets(1, 3, 5) >> [SmileCashSaveQueueEntity_생성(), SmileCashSaveQueueEntity_생성(seqNo: 2L)]
    2 * memberService.findSmileUserKey(_ as String) >> "smileUserKey"
    2 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, "basic smileUserKey") >> Optional.of(SmileCashResponseDto_생성()) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    1 * smileCashSaveQueueRepository.update(_ as Long, 4, 0, _ as String) >> {}
    1 * smileCashSaveQueueRepository.update(_ as Long, 3, 1, _ as String) >> {}
  }
}
