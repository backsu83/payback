package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward
import com.ebaykorea.payback.core.dto.event.ApprovalEventRewardRequestDto

import java.time.Instant

import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_FORMATTER

class ApprovalGrocery {

  static def ApprovalEventRewardRequestDto_생성(Map map = [:]) {
    new ApprovalEventRewardRequestDto().tap {
      authorizationId = (map.authorizationId ?: "authorizationId") as String
      shopTransactionId = (map.shopTransactionId ?: "shopTransactionId") as String
      shopOrderNo = (map.shopOrderNo ?: "") as String
      shopManageCode = (map.shopManageCode ?: "") as String
      shopComment = (map.shopComment ?: "") as String
      promotionId = (map.promotionId ?: "") as String
      totalApprovalAmount = (map.totalApprovalAmount ?: 0L) as BigDecimal
      transactionDate = (map.transactionDate ?: "2024-03-27 00:00:00") as String
      expireDate = (map.expireDate ?: "2024-04-01") as String
    }
  }

  static def ApprovalEventReward_생성(Map map = [:]) {
    new ApprovalEventReward(
        (map.savingNo ?: 1L) as long,
        (map.authorizationId ?: "authorizationId") as String,
        (map.shopManageCode ?: "") as String,
        (map.shopComment ?: "") as String,
        (map.saveAmount ?: 0) as BigDecimal,
        (map.transactionDate ?: DATE_TIME_FORMATTER.parse("2024-03-27 00:00:00", Instant::from)) as Instant,
        (map.expireDate ?: DATE_FORMATTER.parse("2024-04-01", Instant::from)) as Instant
    )
  }
}
