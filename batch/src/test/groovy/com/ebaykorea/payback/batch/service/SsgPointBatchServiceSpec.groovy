package com.ebaykorea.payback.batch.service


import com.ebaykorea.payback.batch.domain.SsgPointTargetDto
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetRecoverWriter
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport
import spock.lang.Specification

import static com.ebaykorea.payback.batch.grocery.SsgPointTargetDtoGrocery.SsgPointTargetDto_생성

class SsgPointBatchServiceSpec extends Specification {

  def ssgPointTargetRepositorySupport = Mock(SsgPointTargetRepositorySupport)
  def ssgPointTargetWriter = new SsgPointTargetWriter(ssgPointTargetRepositorySupport)
  def ssgPointTargetRecoverWriter = new SsgPointTargetRecoverWriter(ssgPointTargetRepositorySupport)

  def "SSG_POINT_TARET_데이터_업데이트"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(orderNo: 111L,
            buyerId: "testUser",
            receiptNo: "GMK0000",
            pointToken: "pointToken",
            requestDate: "20230411110717", //신세계 API 호출시간
            saveAmount: 10L,
            accountDate: "20230411",
            responseCode: API응답결과,
            status: PointStatusType.Ready,
            tradeType: PointTradeType.Save,
            dupApprid: 중복승인번호
    )

    when:
    ssgPointTargetRecoverWriter.updateWriterRecoverSuceess(ssgPointTargetDto)

    then:
    결과 * ssgPointTargetRepositorySupport.updatePointTarget(_ as SsgPointTargetDto, _ as BigDecimal, _ as String, _ as Boolean, _ as String)

    where:
    결과 | API응답결과 | 중복승인번호
    1 | "API0000" | "APPRID0000"
    1 | "PRC4081" | "APPRID0000"
    0 | "PRC4081" | null
    1 | "PRC0000" | "APPRID0000"
  }

  def "SSG_POINT_TARET_데이터_취소대기_업데이트"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(orderNo: 111L,
            buyerId: "testUser",
            receiptNo: "GMK0000",
            pointToken: "pointToken",
            requestDate: "20230411110717", //신세계 API 호출시간
            saveAmount: 10L,
            accountDate: "20230411",
            responseCode: "API0000",
            status: PointStatusType.Ready,
            tradeType: 포인트타입
    )

    when:
    ssgPointTargetWriter.updateWriterSuceess(ssgPointTargetDto)

    then:
    취소대기업데이트 * ssgPointTargetRepositorySupport.updatePntApprIdForCancelTradeType(_ as SsgPointTargetDto)
    1 * ssgPointTargetRepositorySupport.updatePointTarget(_ as SsgPointTargetDto, _ as BigDecimal, _ as String, _ as Boolean, _ as String)

    where:
    취소대기업데이트 | 포인트타입
    1 | PointTradeType.Save
    0 | PointTradeType.Cancel
  }


  def "SSG_POINT_TARET_실패건_데이터_업데이트"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(orderNo: 111L,
            buyerId: "testUser",
            receiptNo: "GMK0000",
            pointToken: "pointToken",
            requestDate: "20230411110717", //신세계 API 호출시간
            saveAmount: 10L,
            pntApprId: "APPRID0000",
            accountDate: "20230411",
            responseCode: "API0000",
            status: PointStatusType.Fail,
            tradeType: PointTradeType.Save
    )
    when:
    ssgPointTargetRecoverWriter.updateWriterRecoverSuceess(ssgPointTargetDto)

    then:
    1 * ssgPointTargetRepositorySupport.updatePointTarget(_ as SsgPointTargetDto, _ as BigDecimal, _ as String, _ as Boolean, _ as String)
  }
}
