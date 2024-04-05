package com.ebaykorea.payback.schedulercluster.grocery

import com.ebaykorea.payback.schedulercluster.model.constant.GmarketSmileCashEventType
import com.ebaykorea.payback.schedulercluster.repository.stardb.entity.SmileCashEventEntity

import java.sql.Timestamp

class SmileCashEventEntityGrocery {
  static def SmileCashEventEntity_생성(Map map = [:]) {
    new SmileCashEventEntity().tap {
      smilePayNo = (map.smilePayNo ?: 1L) as Long
      saveIntegrationType = (map.saveIntegrationType ?: "L") as String
      approvalStatus = (map.approvalStatus ?: 0) as int
      smilecashCd = (map.smilecashCd ?: GmarketSmileCashEventType.Unknown) as GmarketSmileCashEventType
      cashBalanceType = (map.cashBalanceType ?: "") as String
      custNo = (map.custNo ?: "12345") as String
      refNo = (map.refNo ?: 0) as int
      eid = (map.eid ?: 0) as int
      ersNo = (map.ersNo ?: 0) as int
      comments = (map.comments ?: "") as String
      requestMoney = (map.requestMoney ?: 0) as BigDecimal
      requestOutputDisabledMoney = (map.requestOutputDisabledMoney ?: 0) as BigDecimal
      orderNo = (map.orderNo ?: 0L) as Long
      expireDate = (map.expireDate ?: Timestamp.valueOf("2024-01-01 00:00:00")) as Timestamp
      tryCount = (map.tryCount ?: 0) as int
      regId = (map.regId ?: "regId") as String
      subShopId = (map.subShopId ?: "GMKT") as String
    }
  }
}
