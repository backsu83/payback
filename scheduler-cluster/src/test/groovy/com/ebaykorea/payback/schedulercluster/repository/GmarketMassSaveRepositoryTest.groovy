package com.ebaykorea.payback.schedulercluster.repository

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.schedulercluster.config.FusionClusterProperties
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper
import com.ebaykorea.payback.schedulercluster.repository.stardb.SmileCashEventRepository
import com.ebaykorea.payback.schedulercluster.service.member.MemberService
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveRequestGrocery.*
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성


class GmarketMassSaveRepositoryTest extends Specification {

  def smileCashEventRepository = Mock(SmileCashEventRepository)
  def memberService = Mock(MemberService)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def fusionClusterProperties = Mock(FusionClusterProperties)
  def mapper = Mappers.getMapper(MassSaveMapper)

  def repository = new GmarketMassSaveRepository(smileCashEventRepository, memberService, smileCashApiClient, mapper, Executors.newFixedThreadPool(5) , fusionClusterProperties)

  def "GmarketMassSaveRepository findTargets 동작 확인"() {

    when:
    smileCashEventRepository.findTargets(_ as Integer , _ as Integer , _ as Integer, _ as Integer, _ as Integer) >> expectResult

    then:
    def result = repository.findTargets(maxRows ,maxRetryConut)
    result.size() == maxRows

    where:
    maxRows | maxRetryConut | expectResult
    1 | 5 | [SmileCashEventEntity_생성()]
    2 | 5 | [SmileCashEventEntity_생성(), SmileCashEventEntity_생성(smilePayNo: 1L)]
    3 | 5 | [SmileCashEventEntity_생성(), SmileCashEventEntity_생성(smilePayNo: 1L), SmileCashEventEntity_생성(smilePayNo: 2L)]

  }

  def "GmarketMassSaveRepository update 성공 동작 확인"() {

    when:
    repository.update(SmileCashEventEntity_생성()).join()

    then:
    1 * memberService.findSmileUserKeyByCustNo(_ as String) >> CompletableFuture.completedFuture("smileUserKey")
    1 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, _ as String) >> Optional.of(SmileCashResponseDto_생성())
    1 * smileCashEventRepository.update(_ as Long, 50, 0, _ as String) >> {}
  }

  def "GmarketMassSaveRepository update 실패 동작 확인"() {

    when:
    repository.update(SmileCashEventEntity_생성()).join()

    then:
    1 * memberService.findSmileUserKeyByCustNo(_ as String) >> CompletableFuture.completedFuture("smileUserKey")
    1 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, _ as String) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    1 * smileCashEventRepository.update(_ as Long, 0, 1, _ as String) >> {}
  }

}
