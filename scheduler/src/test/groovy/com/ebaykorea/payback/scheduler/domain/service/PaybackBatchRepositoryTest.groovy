package com.ebaykorea.payback.scheduler.domain.service

import com.ebaykorea.payback.scheduler.domain.entity.PaybackBatchRecord
import com.ebaykorea.payback.scheduler.domain.mapper.PaybackBatchRecordMapper
import com.ebaykorea.payback.scheduler.infrastructure.repository.CashbackOrderBatchRepository
import com.ebaykorea.payback.scheduler.infrastructure.repository.entity.CashbackOrderBatchEntity
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
    def records = [PaybackBatchRecord.builder()
                           .orderKey("orderKey1")
                           .txKey("teKey1")
                           .retryCount(0L)
                           .status("TARGET")
                           .build()]

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

    cashbackOrderBatchRepository.findNoCompleted() >> targets


    when:
    paybackBatchRepository.getRecords()

    then:
    1 * cashbackOrderBatchRepository.findNoCompleted()
  }

  def "UpdateStatus"() {
  }
}
