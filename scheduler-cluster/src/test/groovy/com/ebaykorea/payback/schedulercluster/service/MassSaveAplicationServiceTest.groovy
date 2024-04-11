package com.ebaykorea.payback.schedulercluster.service

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper
import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent
import com.ebaykorea.payback.schedulercluster.repository.MassSaveRepository
import com.ebaykorea.payback.schedulercluster.service.member.MemberService
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveEventGrocery.MassSaveEvent_생성
import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveRequestGrocery.*

class MassSaveAplicationServiceTest extends Specification {

  def memberService = Mock(MemberService)
  def massSaveRepository = Mock(MassSaveRepository)
  def smileCashApiClient = Mock(SmileCashApiClient)
  def mapper = Mappers.getMapper(MassSaveMapper)

  def service = new MassSaveAplicationService(memberService, massSaveRepository, smileCashApiClient, mapper, Executors.newFixedThreadPool(5))

  def "MassSaveRequestService 적립 요청 동작 확인"() {
    when:
    service.requsetMassSave(3,5)

    then:
    1 * massSaveRepository.findTargets(3, 5) >> [MassSaveEvent_생성(shopOrderId: 1000, amount: 10, shopManageCode: "GN", shopTransactionId: "3", promotionId: "1000", shopComment: "일반 상품평 적립", status: 3, operator: "test", subShopId: "GMKT"), MassSaveEvent_생성(shopOrderId: 1000, amount: 10, shopManageCode: "GN", shopTransactionId: "3", promotionId: "1000", shopComment: "일반 상품평 적립", status: 3, operator: "test", subShopId: "GMKT"), MassSaveEvent_생성(shopOrderId: 1000, amount: 10, shopManageCode: "GN", shopTransactionId: "3", promotionId: "1000", shopComment: "일반 상품평 적립", status: 3, operator: "test", subShopId: "GMKT")]
    3 * memberService.findSmileUserKeyAsync(_ as String) >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("smileUserKey") >> CompletableFuture.completedFuture("")
    2 * smileCashApiClient.requestMassSave(_ as MassSaveRequestDto, "basic smileUserKey") >> Optional.of(SmileCashResponseDto_생성()) >> Optional.of(SmileCashResponseDto_생성(returnCode: "T099"))
    2 * massSaveRepository.updateRetryCount(_ as MassSaveEvent)
    1 * massSaveRepository.updateSaveStatus(_ as String, _ as MassSaveEvent)
  }

}
