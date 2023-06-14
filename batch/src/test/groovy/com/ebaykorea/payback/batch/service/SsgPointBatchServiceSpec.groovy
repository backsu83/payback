package com.ebaykorea.payback.batch.service


import com.ebaykorea.payback.batch.domain.SsgPointTargetDto
import com.ebaykorea.payback.batch.domain.constant.PointStatusType
import com.ebaykorea.payback.batch.domain.constant.PointTradeType
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetRecoverWriter
import com.ebaykorea.payback.batch.job.writer.SsgPointTargetWriter
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointTargetEntity
import spock.lang.Specification

import static com.ebaykorea.payback.batch.grocery.SsgPointTargetDtoGrocery.SsgPointTargetDto_생성

class SsgPointBatchServiceSpec extends Specification {

  def ssgPointTargetRepositorySupport = Mock(SsgPointTargetRepositorySupport)
  def ssgPointTargetWriter = new SsgPointTargetWriter(ssgPointTargetRepositorySupport)
  def ssgPointTargetRecoverWriter = new SsgPointTargetRecoverWriter(ssgPointTargetRepositorySupport)

  def "신세계포인트_실패배치_중복호출처리"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(
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

  def "신세계포인트_실패배치_전문오류"() {

    given:
    var ssgPointTargetDto = SsgPointTargetDto_생성(orderNo: 111L,
            responseCode: API응답결과,
            status: PointStatusType.Fail,
            tradeType: PointTradeType.Cancel,
    )
    ssgPointTargetRepositorySupport.findPointStatusForSucess(ssgPointTargetDto) >> 원적립거래조회

    when:
    ssgPointTargetRecoverWriter.updateWriterRecoverSuceess(ssgPointTargetDto)

    then:
    결과 * ssgPointTargetRepositorySupport.updatePointTarget(_ as SsgPointTargetDto, _ as BigDecimal, _ as String, _ as Boolean, _ as String)
    재시도결과 * ssgPointTargetRepositorySupport.updateTryCountForCancelTradeType(_ as SsgPointTargetDto)

    where:
    결과 | 재시도결과 | API응답결과 | 원적립거래조회
    1 | 0 | "API0000" | null
    0 | 0 | "API0100" | new SsgPointTargetEntity()
    0 | 1 | "API0100" | null
  }
}
