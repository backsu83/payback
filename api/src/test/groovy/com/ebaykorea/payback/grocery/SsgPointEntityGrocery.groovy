package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity

import java.time.Instant

class SsgPointEntityGrocery {
  static def SsgPointTargetEntity_생성(Map map = [:]) {
    SsgPointTargetEntity.builder()
        .orderNo((map.orderNo ?: 1000000000L) as Long)
        .buyerId((map.buyerId ?: "buyerId") as String)
        .siteType((map.siteType ?: "G") as String)
        .tradeType((map.tradeType ?: "S") as String)
        .receiptNo((map.receiptNo ?: "GMK230411220000S0000") as String)
        .orgReceiptNo((map.orgReceiptNo ?: null) as String)
        .pntApprId((map.pntApprId ?: null) as String)
        .orgPntApprId((map.orgPntApprId ?: null) as String)
        .payAmount((map.payAmount ?: 1000L) as BigDecimal)
        .saveAmount((map.saveAmount ?: 1000L) as BigDecimal)
        .pointStatus((map.pointStatus ?: "RR") as String)
        .cancelYn((map.cancelYn ?: "N") as String)
        .pointToken((map.pointToken ?: null) as String)
        .orderDate((map.orderDate ?: TestConstant.SSGPOINT_ORDER_DATE) as Instant)
        .scheduleDate((map.scheduleDate ?: TestConstant.SSGPOINT_SCHEDULE_DATE) as Instant)
        .requestDate((map.requestDate ?: null) as Instant)
        .accountDate((map.accountDate ?: null) as String)
        .responseCode((map.responseCode ?: null) as String)
        .trcNo((map.trcNo ?: "S1041312000000000000") as String)
        .tradeNo((map.tradeNo ?: "1010000000") as String)
        .packNo((map.packNo ?: 1L) as Long)
        .manualOprt((map.manualOprt ?: "adminId") as String)
        .adminCancelYn((map.adminCancelYn ?: "N") as String)
        .tryCount((map.tryCount ?: 0L) as Long)
        .insertOperator((map.insertOperator ?: "adminId") as String)
        .build()
  }
}
