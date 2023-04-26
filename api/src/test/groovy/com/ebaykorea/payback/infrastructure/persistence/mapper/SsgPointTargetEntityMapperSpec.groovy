package com.ebaykorea.payback.infrastructure.persistence.mapper


import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.grocery.SsgPointEntityGrocery.SsgPointTargetEntity_생성
import static com.ebaykorea.payback.grocery.SsgPointGrocery.*
import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_FORMATTER

class SsgPointTargetEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SsgPointTargetEntityMapper)

  def "SsgPointTargetResponseDto 로 전환이 잘 되는지 확인한다"() {
    expect:
    def result = mapper.mapToSsgTarget(
        SsgPointTargetEntity_생성(
            orderDate: Instant.parse("2023-04-11T13:00:00.00Z"),
            scheduleDate: DATE_TIME_FORMATTER.parse("2023-04-16 22:00:00", Instant::from),
            insertOperator: "adminId"
        )
    )

    result == SsgPointTarget_생성(adminId: "adminId")
  }

  def "SsgPointTargetEntity 로 전환이 잘 되는지 확인한다"() {
    when:
    def result = mapper.map(SSG포인트, SSG포인트유닛)

    then:
    def expect = 예상결과
    with(result) {
      orderNo == expect.orderNo
      buyerId == expect.buyerId
      siteType == expect.siteType
      tradeType == expect.tradeType
      receiptNo == expect.receiptNo
      orgReceiptNo == expect.orgReceiptNo
      pntApprId == expect.pntApprId
      orgPntApprId == expect.orgPntApprId
      payAmount == expect.payAmount
      saveAmount == expect.saveAmount
      pointStatus == expect.pointStatus
      cancelYn == expect.cancelYn
      pointToken == expect.pointToken
      orderDate == expect.orderDate
      scheduleDate == expect.scheduleDate
      requestDate == expect.requestDate
      accountDate == expect.accountDate
      responseCode == expect.responseCode
      //trcNo == expect.trcNo //TODO
      tradeNo == expect.tradeNo
      packNo == expect.packNo
      manualOprt == expect.manualOprt
      adminCancelYn == expect.adminCancelYn
      tryCount == expect.tryCount
    }

    where:
    _________________________________________________
    desc | 예상결과 | _
    "적립준비상태" | SsgPointTargetEntity_생성() | _
    "적립취소상태" | SsgPointTargetEntity_생성(tradeType: "C", receiptNo: "GMK230411220000C0000", trcNo: "C1041312000000000000", tradeNo: "2010000000", accountDate: "20230416", pointToken: "pointToken") | _
    "적립보류상태" | SsgPointTargetEntity_생성(pointStatus: "WW") | _
    _________________________________________________
    SSG포인트 | SSG포인트유닛
    SsgPoint_생성() | SsgPointUnit_준비상태_생성()
    SsgPoint_생성() | SsgPointUnit_취소상태_생성()
    SsgPoint_생성() | SsgPointUnit_보류상태_생성()
  }
}
