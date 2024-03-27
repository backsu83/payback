package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveApprovalEntity

import java.sql.Timestamp

class SmileCashSaveApprovalEntityGrocery {
  static def SmileCashSaveApprovalEntity_생성(Map map = [:]) {
    new SmileCashSaveApprovalEntity().tap {
      txId = (map.txId ?: 1L) as long
      smileCashTxId = (map.smileCashTxId ?: "") as String
      smileUserKey = (map.smileUserKey ?: "") as String
      txnType = (map.txnType ?: 0) as int
      saveDate = (map.saveDate ?: null) as Timestamp
      smileCashType = (map.smileCashType ?: 0) as int
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      expireDate = (map.expireDate ?: null) as Timestamp
      diffProcBaseDate = (map.diffProcBaseDate ?: null) as Timestamp
      diffProcIs = (map.diffProcIs ?: 0) as int
      reasonCode = (map.reasonCode ?: "") as String
      reasonComment = (map.reasonComment ?: "") as String
      additionalReasonComment = (map.additionalReasonComment ?: "") as String
      bizType = (map.bizType ?: 0) as int
      bizKey = (map.bizKey ?: "") as String
      insertOperator = (map.insertOperator ?: "payback-api") as String
    }
  }
}
