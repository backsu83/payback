package com.ebaykorea.payback.scheduler.mapper

import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp

import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SaveResultDto_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveApprovalEntity_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.scheduler.grocery.MassSaveRequestGrocery.성공_SmileCashSaveResultDto_생성

class SmileCashSaveMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveMapper)

  def "SmileCashSaveMapper 확인"() {
    expect:
    def result = mapper.map(
        SaveResultDto_생성(
            authorizationId: "authorizationId",
            shopTransactionId: "1",
            smileCash: 성공_SmileCashSaveResultDto_생성()),
        "smileUserKey",
        SmileCashSaveQueueEntity_생성(
            bizKey: "1",
            additionalReasonComment: "additionalReasonComment"))
    result == SmileCashSaveApprovalEntity_생성(
        txId: "1",
        smileCashTxId: "authorizationId",
        smileUserKey: "smileUserKey",
        txnType: 3,
        transactionDate: Timestamp.valueOf("2024-01-18 21:11:20.123"),
        smileCashType: 1,
        saveAmount: 10,
        expireDate: Timestamp.valueOf("2024-01-01 00:00:00"),
        diffProcBaseDate: Timestamp.valueOf("2024-01-18 21:11:20.123"),
        diffProcIs: 1,
        reasonCode: "RM04Y",
        reasonComment: "구매후기 - 별점 평가 적립",
        additionalReasonComment: "additionalReasonComment",
        bizType: 9,
        bizKey: "1",
        insertOperator: "payback-scheduler")
  }
}
