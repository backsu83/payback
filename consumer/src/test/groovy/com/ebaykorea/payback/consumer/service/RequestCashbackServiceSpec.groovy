package com.ebaykorea.payback.consumer.service

import com.ebaykorea.payback.consumer.client.PaybackApiClient
import com.ebaykorea.payback.consumer.client.dto.PaybackRequestDto
import com.ebaykorea.payback.consumer.client.dto.PaybackResponseDto
import com.ebaykorea.payback.consumer.repository.stardb.CashbackOrderFailRepository
import spock.lang.Specification

class RequestCashbackServiceSpec extends Specification {

  def paybackApiClient = Mock(PaybackApiClient)
  def cashbackOrderFailRepository = Mock(CashbackOrderFailRepository)


  def requestCashbackService = new RequestCashbackService(
          paybackApiClient,
          cashbackOrderFailRepository
  )

  def "페이백_캐시백_적립API_성공"() {
    given:
    var response = PaybackResponseDto.builder().code(200).message("SUCESS").build()
    paybackApiClient.saveCashbacks(_ as PaybackRequestDto) >> Optional.of(response)

    when:
    requestCashbackService.requestCashback(_ as String , _ as String)

    then:
    0 * cashbackOrderFailRepository.save(_ as String, _ as String, _ as Long, _ as String, _ as String,)
  }

  def "페이백_캐시백_적립API_실패"() {
    given:
    var response = PaybackResponseDto.builder().code(400).message("SUCESS").build()
    paybackApiClient.saveCashbacks(_ as PaybackRequestDto) >> Optional.of(response)

    when:
    requestCashbackService.requestCashback(_ as String , _ as String)

    then:
    1 * cashbackOrderFailRepository.save(_ as String, _ as String, _ as Long, _ as String, _ as String,)
  }
}
