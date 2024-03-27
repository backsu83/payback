package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward

class ApprovalGrocery {

  static def ApprovalEventReward_생성(Map map = [:]) {
    new ApprovalEventReward(
        (map.savingNo ?: 1L) as long,
        (map.smileUserKey ?: "smileUserKey") as String,
        (map.authorizationId ?: "authorizationId") as String,
        (map.shopTransactionId ?: "shopTransactionId") as String,
        (map.shopOrderNo ?: "") as String,
        (map.shopManageCode ?: "") as String,
        (map.shopComment ?: "") as String,
        (map.promotionId ?: "") as String,
        (map.totalApprovalAmount ?: 0) as BigDecimal,
        (map.transactionDate ?: "2024-03-27 00:00:00") as String,
        (map.expireDate ?: "") as String
    )
  }
}
