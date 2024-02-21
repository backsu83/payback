package com.ebaykorea.payback.scheduler.service

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultRequestDto
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity
import com.ebaykorea.payback.scheduler.service.member.MemberService
import com.ebaykorea.payback.scheduler.service.smilecash.SmileCashApprovalService
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.*

class MassSaveRequestServiceSpec extends Specification {
  def smileCashSaveQueueRepository = Mock(SmileCashSaveQueueRepository)
  def memberService = Mock(MemberService)
  def smileCashApprovalService = Mock(SmileCashApprovalService)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def mapper = Mappers.getMapper(MassSaveRequestMapper)

  def service = new MassSaveRequestService(smileCashSaveQueueRepository, memberService, smileCashApprovalService, smileCashApiClient, mapper, Executors.newFixedThreadPool(5))

  def "MassSaveRequestService 적립 요청 동작 확인"() {
    when:
    service.requestMassSave(5,5)

    then:
    1 * smileCashSaveQueueRepository.findTargets(5, 3, 5) >> [SmileCashSaveQueueEntity_생성(), SmileCashSaveQueueEntity_생성(seqNo: 2L), SmileCashSaveQueueEntity_생성(seqNo: 3L)]
    3 * memberService.findSmileUserKeyAsync(_ as String) >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("")
    2 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, "basic smileUserKey") >> Optional.of(SmileCashResponseDto_생성()) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    1 * smileCashSaveQueueRepository.update(_ as Long, 4, 0, _ as String) >> {}
    2 * smileCashSaveQueueRepository.update(_ as Long, 3, 1, _ as String) >> {}
  }

  def "MassSaveRequestService 적립 확인 동작 확인"() {
    when:
    service.checkSmileCashStatusThenUpdateResult(5, 5)

    then:
    1 * smileCashSaveQueueRepository.findTargets(5, 4, 5) >> [SmileCashSaveQueueEntity_생성(saveStatus: 4), SmileCashSaveQueueEntity_생성(seqNo: 2L, saveStatus: 4), SmileCashSaveQueueEntity_생성(seqNo: 3L, saveStatus: 4)]
    3 * memberService.findSmileUserKeyAsync(_ as String) >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("")
    2 * smileCashApiClient.findSaveResult(_ as SaveResultRequestDto, "basic smileUserKey") >> Optional.of(SaveResultResponseDto_생성(authorizationId: "authorizationId", shopTransactionId: "1", smileCash: 성공_SmileCashSaveResultDto_생성())) >> Optional.of(SaveResultResponseDto_생성())
    1 * smileCashApprovalService.setApproved(_ as SaveResultDto, _ as String, _ as SmileCashSaveQueueEntity) >> {}
    2 * smileCashSaveQueueRepository.update(_ as Long, 4, 1, _ as String) >> {}
  }
}
