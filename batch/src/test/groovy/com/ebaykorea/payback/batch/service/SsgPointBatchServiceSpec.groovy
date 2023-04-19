package com.ebaykorea.payback.batch.service

import com.ebaykorea.payback.batch.client.smileclub.SmileClubApiClient
import com.ebaykorea.payback.batch.client.ssgpoint.SsgPointApiClient
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType
import com.ebaykorea.payback.batch.job.mapper.SsgPointCancelProcesserMapper
import com.ebaykorea.payback.batch.job.mapper.SsgPointEarnProcesserMapper
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.time.Instant

import static com.ebaykorea.payback.batch.grocery.SsgPointTargetDtoGrocery.SsgPointTargetDto_생성

class SsgPointBatchServiceSpec extends Specification {

  def ssgPointApiClient = Mock(SsgPointApiClient)
  def smileClubApiClient = Mock(SmileClubApiClient)
  def ssgTokenRepository = Mock(SsgTokenRepository)
  def earnMapper = Mappers.getMapper(SsgPointEarnProcesserMapper)
  def cancelMapper = Mappers.getMapper(SsgPointCancelProcesserMapper)
  def ssgPointTargetRepositorySupport = Mock(SsgPointTargetRepositorySupport)

  def pointService = new SsgPointBatchService(
          ssgPointApiClient,
          smileClubApiClient,
          ssgTokenRepository,
          earnMapper,
          cancelMapper,
          ssgPointTargetRepositorySupport
  )

  def "SSG_POINT_TARET_데이터_업데이트"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(orderNo: 111L,
            buyerId: "testUser",
            receiptNo: "GMK0000",
            pointToken: "pointToken",
            requestDate: "20230411110717", //신세계 API 호출시간
            saveAmount: 10L,
            pntApprId: "APPRID0000",
            accountDate: "20230411",
            responseCode: API응답결과,
            status: 포인트상태,
            tradeType: PointTradeType.Save
    )
    when:
    pointService.updateWriterSuceess(ssgPointTargetDto)

    then:
    결과 * ssgPointTargetRepositorySupport.updatePointTarget(_ as Long, _ as String, _ as String, _ as OrderSiteType, _ as PointTradeType, _ as String, _ as Instant, _ as String, _ as String, _ as BigDecimal, _ as String)

    where:
    결과 | API응답결과 | 포인트상태
    1 | "API0000" | PointStatusType.Ready
    0 | "PRC4081" | PointStatusType.Fail
    1 | "PRC0000" | PointStatusType.Ready

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
    pointService.updateWriterRecoverSuceess(ssgPointTargetDto)

    then:
    1 * ssgPointTargetRepositorySupport.updatePointTarget(_ as Long, _ as String, _ as String, _ as OrderSiteType, _ as PointTradeType, _ as String, _ as Instant, _ as String, _ as String, _ as BigDecimal, _ as String)
  }
}
