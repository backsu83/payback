package com.ebaykorea.payback.infrastructure.persistence.repository.auction


import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashTransactionRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveQueueEntityMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.MemberEventRewardRequestDto_생성
import static com.ebaykorea.payback.grocery.MemberEventRewardDtoGrocery.MemberEventRewardResultDto_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성

class AuctionSmileCashEventRepositorySpec extends Specification {
  def queueRepository = Mock(SmileCashSaveQueueRepository)
  def transactionRepository = Mock(SmileCashTransactionRepository)
  def mapper = Mappers.getMapper(SmileCashSaveQueueEntityMapper.class)

  def repository = new AuctionSmileCashEventRepository(queueRepository, transactionRepository, mapper)

  def "중복요청 여부에 따라 저장 호출을 올바르게 한다"() {
    when:
    repository.save("memberKey", request)

    then:
    BizKey조회횟수 * queueRepository.findByBizKey(_ as String) >> BizKey조회결과
    txId채번횟수 * transactionRepository.getIacTxId(_ as String) >> 2L
    적립요청횟수 * queueRepository.save(_ as SmileCashSaveQueueEntity) >> {}

    where:
    _________________________________________________
    desc | BizKey조회결과 | request
    "중복요청이 있을 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | MemberEventRewardRequestDto_생성(requestNo: 1L)
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | MemberEventRewardRequestDto_생성(requestNo: 5L)
    "BizKey 조회건이 없는 경우" | [] | MemberEventRewardRequestDto_생성(requestNo: 5L)
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

    expect:
    def result = repository.save("memberKey", request)
    result == Optional.of(expectResult)

    where:
    _________________________________________________
    desc | BizKey조회결과 | BizKey조회결과2 | _
    "중복요청이 있을 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM02Y")] | [] | _
    "BizKey 조회건이 있지만 중복요청이 아닌 경우" | [SmileCashSaveQueueEntity_생성(bizType: 9, reasonCode: "RM01Y")] | [] | _
    "BizKey 조회건이 없는 경우" | [] | [] | _
    _________________________________________________
    request | expectResult | _
    MemberEventRewardRequestDto_생성(requestNo: 1L) | MemberEventRewardResultDto_생성(requestNo: 1L, smilePayNo: 1L, resultCode: -322) | _
    MemberEventRewardRequestDto_생성(requestNo: 5L) | MemberEventRewardResultDto_생성(requestNo: 5L, smilePayNo: 2L, resultCode: 0) | _
    MemberEventRewardRequestDto_생성(requestNo: 5L) | MemberEventRewardResultDto_생성(requestNo: 5L, smilePayNo: 2L, resultCode: 0) | _
  }
}