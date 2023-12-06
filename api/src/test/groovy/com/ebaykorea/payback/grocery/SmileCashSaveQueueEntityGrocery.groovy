package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashReasonCodeEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity

import java.sql.Timestamp

class SmileCashSaveQueueEntityGrocery {
  static def SmileCashSaveQueueEntity_생성(Map map = [:]) {
    new SmileCashSaveQueueEntity().tap{
      seqNo = (map.seqNo ?: 0L) as long
      txId = (map.txId ?: 1L) as long
      memberId = (map.memberId ?: "memberKey") as String
      reasonCode = (map.reasonCode ?: "") as String
      reasonComment = (map.reasonComment ?: null) as String
      additionalReasonComment = (map.additionalReasonComment ?: null) as String
      bizType = (map.bizType ?: 0) as Integer
      bizKey = (map.bizKey ?: "") as String
      smileCashType = (map.smileCashType ?: 0) as int
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      saveStatus = (map.saveStatus ?: 0) as int
      expireDate = (map.expireDate ?: Timestamp.from(TestConstant.ORDER_DATE)) as Timestamp
      insertOperator = (map.insertOperator ?: "") as String
    }
  }

  static def SmileCashReasonCodeEntity_생성(Map map = [:]) {
    new SmileCashReasonCodeEntity().tap{
      reasonCode = (map.reasonCode ?: "") as String
      iacReasonComment = (map.iacReasonComment ?: null) as String
      additionalComment = (map.additionalComment ?: null) as String
    }
  }
}
