package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveApprovalEntity

import java.sql.Timestamp

class SmileCashSaveApprovalEntityGrocery {
  static def SmileCashSaveApprovalEntity_생성(Map map = [:]) {
    new SmileCashSaveApprovalEntity().tap {
      txId = (map.txId ?: 1L) as long
      saveDate = (map.saveDate ?: null) as Timestamp
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      reasonCode = (map.reasonCode ?: "") as String
    }
  }
}
