package com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper

import com.ebaykorea.payback.constant.TestConstant
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant

import static com.ebaykorea.payback.grocery.ApprovalGrocery.ApprovalEventReward_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveApprovalEntityGrocery.SmileCashSaveApprovalEntity_생성
import static com.ebaykorea.payback.grocery.SmileCashSaveQueueEntityGrocery.SmileCashSaveQueueEntity_생성
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_FORMATTER

class SmileCashSaveApprovalEntityMapperSpec extends Specification {
  def mapper = Mappers.getMapper(SmileCashSaveApprovalEntityMapper.class)

  def "SmileCashSaveApprovalEntityMapper 확인"() {
    expect:
    def result = mapper.map(
        ApprovalEventReward_생성(saveAmount: 10, shopManageCode: "RM03Y"),
        SmileCashSaveQueueEntity_생성(bizType: 1, bizKey: "11", smileCashType: 1, smileUserKey: "smileUserKey")
    )

    result == SmileCashSaveApprovalEntity_생성(
        smileCashTxId: "authorizationId",
        smileUserKey: "smileUserKey",
        txnType: 3,
        saveDate: Timestamp.from(DATE_TIME_FORMATTER.parse("2024-03-27 00:00:00", Instant::from)),
        smileCashType: 1,
        saveAmount: 10,
        expireDate: Timestamp.from(DATE_FORMATTER.parse("2024-04-01", Instant::from)),
        diffProcBaseDate: Timestamp.from(DATE_TIME_FORMATTER.parse("2024-03-27 00:00:00", Instant::from)),
        diffProcIs: 1,
        reasonCode: "RM03Y",
        bizType: 1,
        bizKey: "11"
    )
  }

}
