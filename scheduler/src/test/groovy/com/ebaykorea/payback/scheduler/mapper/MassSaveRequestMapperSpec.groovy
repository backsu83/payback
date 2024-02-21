package com.ebaykorea.payback.scheduler.mapper

import com.ebaykorea.payback.scheduler.model.constant.AuctionSmileCashEventType
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.MassSaveRequestDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveQueueEntity_생성

class MassSaveRequestMapperSpec extends Specification {
  def mapper = Mappers.getMapper(MassSaveRequestMapper)

  def "MassSaveRequestMapper 확인"() {
    expect:
    def result = mapper.map(SmileCashSaveQueueEntity_생성(bizKey: "3", saveAmount: 10, auctionSmileCashEventType: AuctionSmileCashEventType.RM04Y, reasonComment: "구매후기 - 별점 평가 적립", additionalReasonComment: "(주문번호: 1)"))
    result == MassSaveRequestDto_생성(shopOrderId: "3", amount: 10, shopManageCode: "RM04Y", promotionId: "APR0001", shopComment: "구매후기 - 별점 평가 적립 (주문번호: 1)")
  }
}
