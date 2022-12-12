package com.ebaykorea.payback.scheduler.domain.service

import com.ebaykorea.payback.scheduler.service.entity.ProcessType
import com.ebaykorea.payback.scheduler.service.mapper.PaybackBatchRecordMapper
import com.ebaykorea.payback.scheduler.repository.CashbackOrderBatchRepository
import com.ebaykorea.payback.scheduler.repository.entity.CashbackOrderBatchEntity
import com.ebaykorea.payback.scheduler.repository.PaybackBatchRepository
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant


class PaybackBatchRepositoryTest extends Specification {

  def cashbackOrderBatchRepository = Mock(CashbackOrderBatchRepository)
  def paybackBatchRecordMapper = Mappers.getMapper(PaybackBatchRecordMapper)
  def paybackBatchRepository = new PaybackBatchRepository(cashbackOrderBatchRepository, paybackBatchRecordMapper)

  def "GetRenacords"() {

    given:
    def targets = [CashbackOrderBatchEntity.builder()
                          .orderKey("orderKey1")
                          .txKey("teKey1")
                          .responseCode(500L)
                          .retryCount(0L)
                          .status("TARGET")
                          .messageCode(null)
                          .insDate(Timestamp.from(Instant.now()))
                          .insOprt("test")
                          .updDate(Timestamp.from(Instant.now()))
                          .updOprt("test")
                          .build()]
    when:
    paybackBatchRepository.getRecords()

    then:
    1 * cashbackOrderBatchRepository.findNoCompleted() >> targets
  }

  def "UpdateStatus"() {

    when:
    paybackBatchRepository.updateStatus(
            "orderKey" ,
            "txKey" ,
            ProcessType.COMPLETED ,
            0L)

    then:
    1 * cashbackOrderBatchRepository.updateSatus(
            "orderKey" ,
            "txKey" ,
            "COMPLETED" ,
            0L ,
            "paybackScheduler")
  }
}
