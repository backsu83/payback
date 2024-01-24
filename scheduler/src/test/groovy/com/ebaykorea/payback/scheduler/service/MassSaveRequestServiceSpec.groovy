package com.ebaykorea.payback.scheduler.service

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultRequestDto
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper
import com.ebaykorea.payback.scheduler.mapper.SmileCashSaveMapper
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveApprovalEntity
import com.ebaykorea.payback.scheduler.service.member.MemberService
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SaveResultResponseDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashResponseDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.성공_SmileCashSaveResultDto_생성

class MassSaveRequestServiceSpec extends Specification {
  def smileCashSaveQueueRepository = Mock(SmileCashSaveQueueRepository)
  def memberService = Mock(MemberService)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def mapper = Mappers.getMapper(MassSaveRequestMapper)
  def smileCashSaveMapper = Mappers.getMapper(SmileCashSaveMapper)

  def service = new MassSaveRequestService(smileCashSaveQueueRepository, memberService, smileCashApiClient, mapper, smileCashSaveMapper, Executors.newFixedThreadPool(5))

  def "MassSaveRequestService 적립 요청 동작 확인"() {
    when:
    service.requestMassSave(1,5)
    Thread.sleep(300) //비동기 호출 대기

    then:
    1 * smileCashSaveQueueRepository.findTargets(1, 3, 5) >> [SmileCashSaveQueueEntity_생성(), SmileCashSaveQueueEntity_생성(seqNo: 2L), SmileCashSaveQueueEntity_생성(seqNo: 3L)]
    3 * memberService.findSmileUserKeyAsync(_ as String) >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("")
    2 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, "basic smileUserKey") >> Optional.of(SmileCashResponseDto_생성()) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    1 * smileCashSaveQueueRepository.update(_ as Long, 4, 0, _ as String) >> {}
    2 * smileCashSaveQueueRepository.update(_ as Long, 3, 1, _ as String) >> {}
  }

  def "MassSaveRequestService 적립 확인 동작 확인"() {
    when:
    service.checkSmileCashStatusThenUpdateResult(1, 5)
    Thread.sleep(300) //비동기 호출 대기

    then:
    1 * smileCashSaveQueueRepository.findTargets(1, 4, 5) >> [SmileCashSaveQueueEntity_생성(saveStatus: 4), SmileCashSaveQueueEntity_생성(seqNo: 2L, saveStatus: 4), SmileCashSaveQueueEntity_생성(seqNo: 3L, saveStatus: 4)]
    3 * memberService.findSmileUserKeyAsync(_ as String) >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("")
    2 * smileCashApiClient.findSaveResult(_ as SaveResultRequestDto, "basic smileUserKey") >> Optional.of(SaveResultResponseDto_생성(authorizationId: "authorizationId", shopTransactionId: "1", smileCash: 성공_SmileCashSaveResultDto_생성())) >> Optional.of(SaveResultResponseDto_생성())
    1 * smileCashSaveQueueRepository.saveApproval(_ as SmileCashSaveApprovalEntity) >> {}
    1 * smileCashSaveQueueRepository.update(_ as Long, 1, 0, _ as String) >> {}
    2 * smileCashSaveQueueRepository.update(_ as Long, 4, 1, _ as String) >> {}
  }
}
