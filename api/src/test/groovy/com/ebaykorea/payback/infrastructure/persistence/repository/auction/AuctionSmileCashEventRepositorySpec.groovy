package com.ebaykorea.payback.infrastructure.persistence.repository.auction

import com.ebaykorea.payback.core.domain.constant.EventType
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashReasonCodeRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper
import com.ebaykorea.payback.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashReasonCodeEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays

class AuctionSmileCashEventRepositorySpec extends Specification {
  def queueRepository = Mock(SmileCashSaveQueueRepository)
  def transactionRepository = Mock(SmileCashTransactionRepository)
  def reasonCodeRepository = Mock(SmileCashReasonCodeRepository)
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def repository = new AuctionSmileCashEventRepository(queueRepository, transactionRepository, reasonCodeRepository, mapper)

  def "중복요청 여부에 따라 저장 호출을 올바르게 한다"() {
    when:
    repository.save(request)

    then:
    BizKey조회횟수 * queueRepository.findByBizKey(_ as String) >> BizKey조회결과
    txId채번횟수 * transactionRepository.getIacTxId(_ as String) >> 2L
    적립요청횟수 * reasonCodeRepository.findById(_ as String) >> Optional.of(SmileCashReasonCodeEntity_생성(reasonCode: "RM01Y"))
    적립요청횟수 * queueRepository.save(_ as SmileCashSaveQueueEntity) >> {}

    where:
    _________________________________________________
    desc | BizKey조회결과 | request
    "중복요청이 있을 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | EventRewardRequestDto_생성(requestNo: 1L, eventType: EventType.Toss)
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | EventRewardRequestDto_생성(requestNo: 5L, eventType: EventType.Toss)
    "BizKey 조회건이 없는 경우" | [] | EventRewardRequestDto_생성(requestNo: 5L)
    _________________________________________________
    BizKey조회횟수 | txId채번횟수 | 적립요청횟수
    1 | 0 | 0
    1 | 1 | 1
    1 | 1 | 1
  }

  def "AuctionSmileCashEventRepository 저장 결과가 정상인지 확인"() {
    setup:
    queueRepository.save(_ as SmileCashSaveQueueEntity) >> {}
    queueRepository.findByBizKey(_ as String) >> BizKey조회결과 >> BizKey조회결과2
    transactionRepository.getIacTxId(_ as String) >> 2L
    reasonCodeRepository.findById(_ as String) >> Optional.of(SmileCashReasonCodeEntity_생성(reasonCode: "RM01Y"))

    expect:
    def result = repository.save(request)
    result == Optional.of(expectResult)

    where:
    _________________________________________________
    desc | BizKey조회결과 | BizKey조회결과2 | _
    "중복요청이 있을 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | [] | _
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | [] | _
    "BizKey 조회건이 없는 경우" | [] | [] | _
    _________________________________________________
    request | expectResult | _
    EventRewardRequestDto_생성(requestNo: 1L, eventType: EventType.Toss) | EventRewardResultDto_생성(requestNo: 1L, smilePayNo: 1L, resultCode: -322) | _
    EventRewardRequestDto_생성(requestNo: 5L, eventType: EventType.Toss) | EventRewardResultDto_생성(requestNo: 5L, smilePayNo: 2L, resultCode: 0) | _
    EventRewardRequestDto_생성(requestNo: 5L) | EventRewardResultDto_생성(requestNo: 5L, smilePayNo: 2L, resultCode: 0) | _
  }

  def "EventType에 별로 SmileCashSaveQueueEntity 맵핑 테스트"() {

    setup:
    def result = mapper.map(1L , "comment", request)

    expect:
    result == expectResult

    where:
    _________________________________________________
    desc | request
    "이벤트 - 토스 사후적립"    | EventRewardRequestDto_생성(eventType: EventType.Toss, memberKey: "user1", requestNo: 1L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 출석체크"         | EventRewardRequestDto_생성(eventType: EventType.DailyCheckIn, memberKey: "user2", requestNo: 2L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 상품평(일발)"     | EventRewardRequestDto_생성(eventType: EventType.Review, memberKey: "user3", requestNo: 3L, saveAmount: 100L, eventNo: 100)
    "이벤트 - 상품평(프리미엄)" | EventRewardRequestDto_생성(eventType: EventType.ReviewPremium, memberKey: "user4", requestNo: 4L, saveAmount: 100L, eventNo: 100)
    _________________________________________________
    expectResult | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 1L, memberId: "user1", reasonCode: "RM02Y", reasonComment: "comment", additionalReasonComment: "comment",  bizType: "9", smileCashType: "2", insertOperator: "user1", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 30))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 2L, memberId: "user2", reasonCode: "RM03Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user2", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 90))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 3L, memberId: "user3", reasonCode: "RM04Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user3", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
    SmileCashSaveQueueEntity_생성(saveAmount: 100L, bizKey: 4L, memberId: "user4", reasonCode: "RM05Y", reasonComment: "comment", additionalReasonComment: "comment", bizType: "9", smileCashType: "2", insertOperator: "user4", referenceKey: "100", expireDate: Timestamp.from(truncatedDays(PaybackInstants.now(), 180))) | _
  }
}
