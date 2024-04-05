package com.ebaykorea.payback.schedulercluster.mapper

import com.ebaykorea.payback.schedulercluster.model.constant.AuctionSmileCashEventType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveRequestGrocery.*
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.schedulercluster.grocery.SmileCashEventEntityGrocery.SmileCashEventEntity_생성

class MassSaveMapperTest extends Specification {
  def mapper = Mappers.getMapper(MassSaveMapper)

  def "MassSaveMapper 옥션 확인"() {
    expect:
    def result = mapper.map(SmileCashSaveQueueEntity_생성(bizKey: "3", saveAmount: 10, auctionSmileCashEventType: AuctionSmileCashEventType.RM04Y, reasonComment: "구매후기 - 별점 평가 적립", additionalReasonComment: "(주문번호: 1)"))
    result == MassSaveRequestDto_생성(shopOrderId: "3", amount: 10, shopManageCode: "RM04Y", promotionId: "APR0001", shopComment: "구매후기 - 별점 평가 적립 (주문번호: 1)", shopId: "S002", subShopId: "IAC")
  }

  def "MassSaveMapper 지마켓 확인"() {
    expect:
    def result = mapper.map(SmileCashEventEntity_생성(smilePayNo: "3", requestMoney: 10, cashBalanceType: "GN", comments: "일반 상품평 적립", ersNo: 1000))
    result == MassSaveRequestDto_생성(shopTransactionId: "3", amount: 10, shopManageCode: "GN", promotionId: "1000", shopComment: "일반 상품평 적립", shopOrderId: 1000, subShopId: "GMKT")
  }
}
