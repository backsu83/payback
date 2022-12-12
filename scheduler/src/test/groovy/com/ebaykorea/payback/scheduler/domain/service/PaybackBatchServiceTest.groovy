package com.ebaykorea.payback.scheduler.domain.service

import com.ebaykorea.payback.scheduler.service.entity.PaybackBatchRecord
import com.ebaykorea.payback.scheduler.service.entity.ProcessType
import com.ebaykorea.payback.scheduler.client.PaybackApiClient
import com.ebaykorea.payback.scheduler.client.dto.PaybackRequestDto
import com.ebaykorea.payback.scheduler.client.dto.PaybackResponseDto
import com.ebaykorea.payback.scheduler.repository.PaybackBatchRepository
import com.ebaykorea.payback.scheduler.service.PaybackBatchService
import spock.lang.Specification
import java.util.concurrent.Executors


class PaybackBatchServiceTest extends Specification {

  def paybackBatchRepository = Mock(PaybackBatchRepository)
  def paybackApiClient = Stub(PaybackApiClient)
  def paybackBatchService = new PaybackBatchService(
          paybackBatchRepository,
          paybackApiClient,
          Executors.newFixedThreadPool(2)
  )

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
    paybackApiClient.saveCashbacks(_ as PaybackRequestDto) >>> response >> {throw new Exception()}

    when:
    paybackBatchService.updateRecords()

    then:
    1 * paybackBatchRepository.updateStatus(_ ,_ , ProcessType.COMPLETED , 0L) >> {}
    1 * paybackBatchRepository.updateStatus(_ ,_ , ProcessType.FAIL , !0L) >> {}
  }

}
