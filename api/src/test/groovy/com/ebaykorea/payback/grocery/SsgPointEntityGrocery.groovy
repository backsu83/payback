package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgPointTargetEntity

import java.time.Instant

class SsgPointEntityGrocery {
  static def SsgPointTargetEntity_생성(Map map = [:]) {
    SsgPointTargetEntity.builder()
        .orderNo((map.orderNo ?: 1L) as Long)
        .buyerId((map.buyerId ?: "buyerId") as String)
        .siteType((map.siteType ?: "G") as String)
        .tradeType((map.tradeType ?: "S") as String)
        .receiptNo((map.receiptNo ?: "GMK230411220000S1") as String)
        .orgReceiptNo((map.receiptNo ?: null) as String)
        .pntApprId((map.pntApprId ?: null) as String)
        .orgPntApprId((map.orgPntApprId ?: null) as String)
        .payAmount((map.payAmount ?: 1000L) as BigDecimal)
        .saveAmount((map.saveAmount ?: 1000L) as BigDecimal)
        .pointStatus((map.pointStatus ?: "RR") as String)
        .cancelYn((map.cancelYn ?: "N") as String)
        .pointToken((map.pointToken ?: "pointToken") as String)
        .orderDate((map.orderDate ?: null) as Instant)
        .scheduleDate((map.scheduleDate ?: null) as Instant)
        .requestDate((map.requestDate ?: null) as Instant)
        .accountDate((map.accountDate ?: "accountDate") as String)
        .responseCode((map.responseCode ?: "responseCode") as String)
        .trcNo((map.trcNo ?: "S1041216000000000000") as String)
        .tradeNo((map.tradeNo ?: "S100000000") as String)
        .packNo((map.packNo ?: 1L) as Long)
        .manualOprt((map.manualOprt ?: "manualOprt") as String)
        .adminCancelYn((map.adminCancelYn ?: "N") as String)
        .tryCount((map.tryCount ?: 0L) as Long)
        .insertDate((map.insertDate ?: null) as Instant)
        .insertOperator((map.insertOperator ?: "adminId") as String)
        .updateDate((map.updateDate ?: null) as Instant)
        .updateOperator((map.updateOperator ?: "adminId") as String)
        .build()
  }
}
