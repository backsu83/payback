package com.ebaykorea.payback.batch.job.mapper

import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointCommonResponse
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointEarnRequest
import com.ebaykorea.payback.batch.domain.SsgPointProcessorDto
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType
import com.ebaykorea.payback.batch.util.PaybackDecimals

import static com.ebaykorea.payback.batch.grocery.SsgPointCertifierGrocery.SsgPointCertifier_생성
import static com.ebaykorea.payback.batch.grocery.SsgPointEarnRequestGrocery.SsgPointEarnRequest_생성
import static com.ebaykorea.payback.batch.grocery.SsgPointProcessorDtoGrocery.SsgPointProcessorDto_생성

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.batch.grocery.SsgPointTargetDtoGrocery.SsgPointTargetDto_생성


class SsgPointEarnProcessorMapperTest extends Specification {
  def mapper = Mappers.getMapper(SsgPointEarnProcessorMapper)


  def "SSG_POINT_적립API_리퀘스트_생성"() {

    expect:
    def result = mapper.mapToRequest(processorDto, certifier, "tokenId", "cardNo" )
    result.setTradeGentdTm("000000")
    result == expectResult

    where:
    _________________________________________________
    desc | processorDto | certifier
    "지마켓 적립 request" | SsgPointProcessorDto_생성(orderNo:111L) | SsgPointCertifier_생성(orderSiteType: OrderSiteType.Gmarket)
    "옥션 적립 reqest" | SsgPointProcessorDto_생성(orderNo:222L) | SsgPointCertifier_생성(orderSiteType: OrderSiteType.Auction)
    _________________________________________________
    expectResult | _ | _
    SsgPointEarnRequest_생성(clientId: "49E615F309BC23C5CA7E4603E2036977", apiKey:"E320844B8E294F3E8D69395737C8B194", brchId:"B200042500", orgSaleTradeNo: 111L) | _ | _
    SsgPointEarnRequest_생성(clientId: "3C24A0D1FADA47F07F9A79D30D4C9A2E", apiKey:"72787780CCA9F00A5D584991826752E2", brchId:"B200042502", orgSaleTradeNo: 222L) | _ | _
  }

  def "SSG_TARGET_DTO_생성"() {

    given:
    var request = SsgPointEarnRequest.builder()
            .busiDt("20230411") //적립날짜 (yyyyMMdd)
            .tradeGentdTm("134010") //적립시간 (HHmmss)
            .cardNo("pointToken") //적립시간 (HHmmss)
            .build()

    var response = SsgPointCommonResponse.builder()
            .pntApprId("APPRID0000")
            .gpoint("10") //적립포인트
            .responseCd("API0000") //적립승인코드
            .build()

    var processor =  SsgPointProcessorDto.builder()
            .orderNo(111L)
            .receiptNo("GMK0000")
            .buyerId("testUser")
            .siteType(OrderSiteType.Gmarket)
            .tradeType(PointTradeType.Save)
            .status(PointStatusType.Ready)
            .build()

    when:
    def result = mapper.mapToTarget(request.getBusiDt(), request.getRequestDate(), request.getCardNo(), response, processor)

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
            tradeType: PointTradeType.Save
    )
  }
}
