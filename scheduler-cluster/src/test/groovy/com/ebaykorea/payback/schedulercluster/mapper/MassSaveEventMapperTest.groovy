package com.ebaykorea.payback.schedulercluster.mapper

import com.ebaykorea.payback.schedulercluster.model.constant.AuctionSmileCashEventType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveEventGrocery.MassSaveEvent_생성
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성

class MassSaveEventMapperTest extends Specification {
  def mapper = Mappers.getMapper(MassSaveEventMapper)

  def "MassSaveEventMapper 확인"() {
    expect:
    def result = mapper.map(request)
    result == expectResult

    where:
    _________________________________________________
    desc | request
    "욕선"   | SmileCashSaveQueueEntity_생성(bizKey: "3", saveAmount: 10, auctionSmileCashEventType: AuctionSmileCashEventType.RM04Y, reasonComment: "구매후기 - 별점 평가 적립", additionalReasonComment: "(주문번호: 1)", memberId:"1", saveStatus: 3, insertOperator: "test")
    "지마켓" | SmileCashEventEntity_생성(smilePayNo: "3", requestMoney: 10, cashBalanceType: "GN", comments: "일반 상품평 적립", ersNo: 1000, custNo:"1", approvalStatus: 3, regId: "test")
    _________________________________________________
    expectResult | _
    MassSaveEvent_생성(shopOrderId: "3", amount: 10, shopManageCode: "RM04Y", promotionId: "APR0001", shopComment: "구매후기 - 별점 평가 적립 (주문번호: 1)", shopId:"S002", status: 3, operator: "test", seqNo: 1, subShopId:"IAC") | _
    MassSaveEvent_생성(shopOrderId: 1000, amount: 10, shopManageCode: "GN", shopTransactionId: "3", promotionId: "1000", shopComment: "일반 상품평 적립", status: 3, operator: "test", subShopId: "GMKT") | _
  }
}
