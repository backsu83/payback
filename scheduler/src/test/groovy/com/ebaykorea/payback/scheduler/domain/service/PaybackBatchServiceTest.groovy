package com.ebaykorea.payback.scheduler.domain.service

import com.ebaykorea.payback.scheduler.domain.entity.PaybackBatchRecord
import com.ebaykorea.payback.scheduler.domain.entity.ProcessType
import com.ebaykorea.payback.scheduler.infrastructure.gateway.client.PaybackApiClient
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackRequestDto
import com.ebaykorea.payback.scheduler.infrastructure.gateway.dto.PaybackResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.util.concurrent.ExecutorService

@SpringBootTest
class PaybackBatchServiceTest extends Specification {

  @Autowired
  ExecutorService taskExecutor

  def paybackBatchRepository = Mock(PaybackBatchRepository)
  def paybackApiClient = Stub(PaybackApiClient)
  def paybackBatchService = new PaybackBatchService(paybackBatchRepository, paybackApiClient, taskExecutor)

  def "UpdateRecords"() {
    given:
    def response = PaybackResponseDto.builder()
            .code("200")
            .data(PaybackResponseDto.Body.builder()
                    .orderKey("orderKey1")
                    .txKey("teKey1")
                    .build())
            .message("CREATED")
            .build()

    def records = [
            PaybackBatchRecord.builder()
                    .orderKey("orderKey1")
                    .txKey("teKey1")
                    .responseCode(500L)
                    .messageCode(null)
                    .status("TARGET")
                    .retryCount(0L)
                    .build(),
             PaybackBatchRecord.builder()
                     .orderKey("orderKey2")
                     .txKey("teKey2")
                     .responseCode(500L)
                     .messageCode(null)
                     .status("TARGET")
                     .retryCount(0L)
                     .build()
    ]

    paybackBatchRepository.getRecords() >> records
    paybackApiClient.saveCashbacks(_ as PaybackRequestDto) >> response

    when:
    paybackBatchService.updateRecords()

    then:
    2 * paybackBatchRepository.updateStatus(_ ,_ ,_ ,_ ) >> {}
  }

  def "Success"() {
    given:
    def paybackResponseDto = [
            PaybackResponseDto.builder()
            .code("200")
            .data(PaybackResponseDto.Body.builder()
                    .orderKey("orderKey1")
                    .txKey("teKey1")
                    .build())
            .message("CREATED")
            .build()
    ]

    when:
    paybackBatchService.success(paybackResponseDto)

    then:
    1 * paybackBatchRepository.updateStatus(_ ,_ ,_ ,_ ) >> {}
  }

  def "Fail"() {

    when:
    paybackBatchService.fail(
            "orderkey" ,
            "txKey" ,
            0L)

    then:
    1 * paybackBatchRepository.updateStatus(
            "orderkey" ,
            "txKey" ,
            ProcessType.FAIL ,
            1L)
  }
}
