package com.ebaykorea.payback.infrastructure.query.auction

import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveApprovalRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.infrastructure.query.mapper.ReviewRewardQueryMapper
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.ReviewRewardQueryResultGrocery.ReviewRewardQueryResult_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveApprovalEntityGrocery.SmileCashSaveApprovalEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성

class AuctionReviewRewardQuerySpec extends Specification {
  def smileCashSaveQueueRepository = Stub(SmileCashSaveQueueRepository)
  def smileCashSaveApprovalRepository = Stub(SmileCashSaveApprovalRepository)
  def mapper = Mappers.getMapper(ReviewRewardQueryMapper)
  def query = new AuctionReviewRewardQuery(smileCashSaveQueueRepository, smileCashSaveApprovalRepository, mapper)

  def "AuctionReviewRewardQuery 테스트"() {
    setup:
    smileCashSaveQueueRepository.findByBizKey(_ as String) >> queues
    smileCashSaveApprovalRepository.findById(_ as Long) >> Optional.ofNullable(approval)

    expect:
    def result = query.getReviewReward("memberKey", 1L)
    result == expectResult

    where:
    desc                    | expectResult                                                                                                                       | queues                                                             | approval
    "요청 내역이 없는 경우"          | []                                                                                                                                 | []                                                                 | null
    "요청 내역은 있지만 적립되지 않은 경우" | []                                                                                                                                 | [SmileCashSaveQueueEntity_생성(reasonCode: "RM04Y")]                 | null
    "적립 결과"                 | [ReviewRewardQueryResult_생성(save: true, saveAmount: 10, saveDate: PaybackInstants.from(Timestamp.valueOf("2024-01-01 10:00:00")))] | [SmileCashSaveQueueEntity_생성(reasonCode: "RM04Y", saveAmount: 10)] | SmileCashSaveApprovalEntity_생성(saveDate: Timestamp.valueOf("2024-01-01 10:00:00"), saveAmount: 10, reasonCode: "RM04Y")
  }
}
