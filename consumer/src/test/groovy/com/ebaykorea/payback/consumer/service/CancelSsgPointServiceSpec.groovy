package com.ebaykorea.payback.consumer.service

import com.ebaykorea.payback.consumer.client.PaybackApiClient
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelRequest
import com.ebaykorea.payback.consumer.client.dto.PaybackSsgPointCancelResponse
import com.ebaykorea.payback.consumer.repository.opayreward.CancelConsumerFailRepository
import com.ebaykorea.payback.consumer.repository.opayreward.entity.CancelConsumerFailEntity
import com.ebaykorea.payback.consumer.service.mapper.impl.GmarketSsgPointMapper
import spock.lang.Specification

class CancelSsgPointServiceSpec extends Specification {

  def paybackApiClient = Mock(PaybackApiClient)
  def cancelConsumerFailRepository = Mock(CancelConsumerFailRepository)
  def mapper = new GmarketSsgPointMapper()

  def requestSsgPointService = new CancelSsgPointService(
      paybackApiClient,
      cancelConsumerFailRepository,
      mapper
  )

  def "페이백_포인트_취소API_지마켓_성공"() {
    given:
    var response = PaybackSsgPointCancelResponse.builder().code(200).message("SUCESS").build()
    var orderNos = [1L, 2L, 3L]
    paybackApiClient.cancelSsgPoint(_ as Long, _ as PaybackSsgPointCancelRequest) >> Optional.of(response)

    when:
    requestSsgPointService.cancelSsgPoint(100L, orderNos)

    then:
    0 * cancelConsumerFailRepository.save(_ as CancelConsumerFailEntity)
  }

  def "페이백_포인트_취소API_호출_실패"() {
    given:
    var orderNos = [1L]
    paybackApiClient.cancelSsgPoint(_ as Long, _ as PaybackSsgPointCancelRequest) >> new RuntimeException("에러")

    when:
    requestSsgPointService.cancelSsgPoint(100L, orderNos)

    then:
    1 * cancelConsumerFailRepository.save(_ as CancelConsumerFailEntity)
  }
}
