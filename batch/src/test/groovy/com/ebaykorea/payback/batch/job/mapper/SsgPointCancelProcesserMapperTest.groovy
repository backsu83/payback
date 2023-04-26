package com.ebaykorea.payback.batch.job.mapper

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCancelRequest
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCommonResponse
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType
import com.ebaykorea.payback.batch.util.PaybackDecimals
import com.ebaykorea.payback.batch.util.PaybackInstants
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.batch.grocery.SsgPointCertifierGrocery.SsgPointCertifier_생성
import static com.ebaykorea.payback.batch.grocery.SsgPointCancelRequestGrocery.SsgPointCancelRequest_생성
import static com.ebaykorea.payback.batch.grocery.SsgPointProcesserDtoGrocery.SsgPointProcesserDto_생성
import static com.ebaykorea.payback.batch.grocery.SsgPointTargetDtoGrocery.SsgPointTargetDto_생성

class SsgPointCancelProcesserMapperTest extends Specification {
  def mapper = Mappers.getMapper(SsgPointCancelProcesserMapper)


  def "SSG_POINT_취소API_리퀘스트_생성"() {

    expect:
    def result = mapper.mapToRequest(processerDto, certifier, "tokenId")
    result.setTradeGentdTm("000000")
    result == expectResult

    where:
    _________________________________________________
    desc | processerDto | certifier
    "지마켓 취소 request" | SsgPointProcesserDto_생성(orderNo:111L,accountDate:"20230411",orgReceiptNo:"GMK00000") | SsgPointCertifier_생성(orderSiteType: OrderSiteType.Gmarket)
    "옥션 취소 reqest" | SsgPointProcesserDto_생성(orderNo:222L,accountDate:"20230411",orgReceiptNo:"AUC00000") | SsgPointCertifier_생성(orderSiteType: OrderSiteType.Auction)
    _________________________________________________
    expectResult | _ | _
    SsgPointCancelRequest_생성(clientId: "49E615F309BC23C5CA7E4603E2036977", apiKey:"E320844B8E294F3E8D69395737C8B194", brchId:"B200042500", orgSaleTradeNo: 111L, otradeRecptNo: "GMK00000") | _ | _
    SsgPointCancelRequest_생성(clientId: "3C24A0D1FADA47F07F9A79D30D4C9A2E", apiKey:"72787780CCA9F00A5D584991826752E2", brchId:"B200042502", orgSaleTradeNo: 222L, otradeRecptNo: "AUC00000") | _ | _
  }

  def "SSG_TARGET_DTO_생성"() {

    given:
    var request = SsgPointCancelRequest.builder()
            .busiDt("20230411") //취소날짜 (yyyyMMdd)
            .tradeGentdTm("134010") //취소시간 (HHmmss)
            .build()

    var response = SsgPointCommonResponse.builder()
            .pntApprId("APPRID0000")
            .gpoint("10") //취소포인트
            .responseCd("API0000") //취소승인코드
            .build()

   var processer =  SsgPointProcesserDto.builder()
           .orderNo(111L)
           .receiptNo("GMK0000")
           .buyerId("testUser")
           .pointToken("pointToken")
           .siteType(OrderSiteType.Gmarket)
           .tradeType(PointTradeType.Cancel)
           .status(PointStatusType.Ready)
           .build()

    when:
    def result = mapper.mapToTarget(request.getBusiDt(), request.getRequestDate(), response, processer)

    then:
    result == SsgPointTargetDto_생성(buyerId: "testUser",
            receiptNo: "GMK0000",
            pointToken: "pointToken",
            requestDate: request.getRequestDate(),
            saveAmount: PaybackDecimals.from(response.getGpoint()),
            pntApprId: "APPRID0000",
            accountDate: "20230411",
            responseCode: "API0000",
            status: PointStatusType.Ready,
            tradeType: PointTradeType.Cancel
    )
  }
}
