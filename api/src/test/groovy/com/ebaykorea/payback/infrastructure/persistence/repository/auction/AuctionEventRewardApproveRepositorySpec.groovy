package com.ebaykorea.payback.infrastructure.persistence.repository.auction

import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveApprovalRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveApprovalEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveApprovalEntityMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002
import static com.ebaykorea.payback.grocery.ApprovalGrocery.ApprovalEventReward_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성

class AuctionEventRewardApproveRepositorySpec extends Specification {
  def approvalRepository = Mock(SmileCashSaveApprovalRepository)
  def saveQueueRepository = Mock(SmileCashSaveQueueRepository)
  def mapper = Mappers.getMapper(SmileCashSaveApprovalEntityMapper.class)

  def repository = new AuctionEventRewardApproveRepository(approvalRepository, saveQueueRepository, mapper)

  def "이미 적립 승인된 적립 요청건에 대한 승인 처리는 실패가 되어야 한다"() {
    setup:
    saveQueueRepository.findByIacTxid(_ as Long) >> Optional.of(SmileCashSaveQueueEntity_생성(saveStatus: 1))

    when:
    repository.approve(ApprovalEventReward_생성())

    then:
    def e = thrown(PaybackException)
    e.code == PERSIST_002
  }

  def "적립 승인 되지 않은 승인 요청건은 승인처리가 되어야 한다"() {
    setup:
    saveQueueRepository.findByIacTxid(_ as Long) >> Optional.of(SmileCashSaveQueueEntity_생성())

    when:
    repository.approve(ApprovalEventReward_생성(transactionDate: "2024-03-27 00:00:00"))

    then:
    1 * approvalRepository.save(_ as SmileCashSaveApprovalEntity) >> {}
    1 * saveQueueRepository.update(1L , 1, "payback-api") >> {}
  }
}
