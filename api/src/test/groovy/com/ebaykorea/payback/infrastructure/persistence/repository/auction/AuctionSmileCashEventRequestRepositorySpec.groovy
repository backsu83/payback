package com.ebaykorea.payback.infrastructure.persistence.repository.auction


import com.ebaykorea.payback.core.exception.PaybackException
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashReasonCodeRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.EventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.EventReward_생성
import static com.ebaykorea.payback.grocery.SmileCashEventGrocery.TossEventReward_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashReasonCodeEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성

class AuctionSmileCashEventRequestRepositorySpec extends Specification {
  def queueRepository = Mock(SmileCashSaveQueueRepository)
  def transactionRepository = Mock(SmileCashTransactionRepository)
  def reasonCodeRepository = Mock(SmileCashReasonCodeRepository)
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def repository = new AuctionSmileCashEventRequestRepository(queueRepository, transactionRepository, reasonCodeRepository, mapper)

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
    desc                          | BizKey조회결과                                                     | request
    "중복요청이 있을 경우"                 | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | TossEventReward_생성()
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | TossEventReward_생성(requestNo: 5L)
    "BizKey 조회건이 없는 경우"           | []                                                             | EventReward_생성(requestNo: 5L, budgetNo: 1L)
    _________________________________________________
    BizKey조회횟수 | txId채번횟수 | 적립요청횟수
    1          | 0        | 0
    1          | 1        | 1
    1          | 1        | 1
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
    desc                          | BizKey조회결과                                                     | BizKey조회결과2 | _
    "중복요청이 있을 경우"                 | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | []          | _
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | []          | _
    "BizKey 조회건이 없는 경우"           | []                                                             | []          | _
    _________________________________________________
    request                           | expectResult                                                           | _
    TossEventReward_생성()              | EventRewardResultDto_생성(requestNo: 1L, savingNo: 1L, resultCode: -322) | _
    TossEventReward_생성(requestNo: 5L) | EventRewardResultDto_생성(requestNo: 5L, savingNo: 2L, resultCode: 0)    | _
    EventReward_생성(requestNo: 5L, budgetNo: 1L)     | EventRewardResultDto_생성(requestNo: 5L, savingNo: 2L, resultCode: 0)    | _
  }

  def "예산 할당 번호에 따라 예산 집행이 되는지 확인"() {
    setup:
    queueRepository.findByBizKey(_ as String) >> [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM03Y")]

    when:
    repository.saveWithBudget(request)

    then:
    예산집행호출_횟수 * queueRepository.updateBudget(_ as Long, _ as BigDecimal) >> 0

    where:
    desc                         | request                                      | 예산집행호출_횟수
    "예산 할당 번호가 있으면 예산집행 처리"      | EventReward_생성(budgetNo: 1L, saveAmount: 10) | 1
  }

  def "예산 집행 처리 결과가 실패 시 exception 발생 여부 확인"() {
    setup:
    queueRepository.updateBudget(_ as Long, _ as BigDecimal) >> -1

    when:
    repository.saveWithBudget(EventReward_생성(budgetNo: 1L, saveAmount: 10))

    then:
    def e = thrown(PaybackException)
    e.code == PERSIST_002
  }

}
