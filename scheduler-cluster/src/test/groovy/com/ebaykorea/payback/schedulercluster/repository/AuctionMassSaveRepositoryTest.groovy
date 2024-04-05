package com.ebaykorea.payback.schedulercluster.repository

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.schedulercluster.config.FusionClusterProperties
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper
import com.ebaykorea.payback.schedulercluster.service.member.MemberService
import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.SmileCashSaveQueueRepository
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveRequestGrocery.*
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성

class AuctionMassSaveRepositoryTest extends Specification {

  def smileCashSaveQueueRepository = Mock(SmileCashSaveQueueRepository)
  def memberService = Mock(MemberService)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def fusionClusterProperties = Mock(FusionClusterProperties)
  def mapper = Mappers.getMapper(MassSaveMapper)

  def repository = new AuctionMassSaveRepository(smileCashSaveQueueRepository, memberService, smileCashApiClient, mapper, Executors.newFixedThreadPool(5) , fusionClusterProperties)

  def "AuctionMassSaveRepository findTargets 동작 확인"() {

    when:
    smileCashSaveQueueRepository.findTargets(_ as Integer , _ as Integer , _ as Integer, _ as Integer, _ as Integer) >> expectResult

    then:
    def result = repository.findTargets(maxRows ,maxRetryConut)
    result.size() == maxRows

    where:
    maxRows | maxRetryConut | expectResult
    1 | 5 | [SmileCashSaveQueueEntity_생성()]
    2 | 5 | [SmileCashSaveQueueEntity_생성(), SmileCashSaveQueueEntity_생성(seqNo: 2L)]
    3 | 5 | [SmileCashSaveQueueEntity_생성(), SmileCashSaveQueueEntity_생성(seqNo: 2L), SmileCashSaveQueueEntity_생성(seqNo: 3L)]

  }

  def "AuctionMassSaveRepository update 성공 동작 확인"() {

    when:
    repository.update(SmileCashSaveQueueEntity_생성()).join()

    then:
    1 * memberService.findSmileUserKeyByMemberId(_ as String) >> CompletableFuture.completedFuture("smileUserKey")
    1 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, _ as String) >> Optional.of(SmileCashResponseDto_생성())
    1 * smileCashSaveQueueRepository.update(_ as Long, 1, 0, _ as String, "smileUserKey") >> {}
  }

  def "AuctionMassSaveRepository update 실패 동작 확인"() {

    when:
    repository.update(SmileCashSaveQueueEntity_생성()).join()

    then:
    1 * memberService.findSmileUserKeyByMemberId(_ as String) >> CompletableFuture.completedFuture("smileUserKey")
    1 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, _ as String) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    1 * smileCashSaveQueueRepository.update(_ as Long, 3, 1, _ as String, "") >> {}
  }

}
