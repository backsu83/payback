package com.ebaykorea.payback.infrastructure.persistence.repository

import com.ebaykorea.payback.infrastructure.persistence.mapper.CashbackEntityMapper
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderDetailRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderMemberRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderPolicyRepository
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.CashbackOrderRepository
import org.mapstruct.factory.Mappers
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class CashbackRepositoryImplTest extends Specification {

    def cashbackOrderRepository = Stub(CashbackOrderRepository)
    def cashbackOrderDetailRepository = Stub(CashbackOrderDetailRepository)
    def cashbackOrderMemberRepository = Stub(CashbackOrderMemberRepository)
    def cashbackOrderPolicyRepository = Stub(CashbackOrderPolicyRepository)
    def cashbackEntityMapper = Mappers.getMapper(CashbackEntityMapper)
    def cashbackRepositoryImpl = new CashbackRepositoryImpl(
            cashbackOrderRepository,
            cashbackOrderDetailRepository,
            cashbackOrderMemberRepository,
            cashbackOrderPolicyRepository,
            cashbackEntityMapper
    )

    //TODO 캐쉬백 도메인 완성후 작성
    def "cashback_저장"() {
        setup:
        cashbackOrderRepository.save( _ as Objects)
        cashbackOrderDetailRepository.save( _ as Objects)
        cashbackOrderMemberRepository.save( _ as Objects)
        cashbackOrderPolicyRepository.save( _ as Objects)

        expect:
        cashbackRepositoryImpl.save(request)
        response

        where:
        desc | request | response
        "저장" | cashback_생성() | "결과"
    }
}
