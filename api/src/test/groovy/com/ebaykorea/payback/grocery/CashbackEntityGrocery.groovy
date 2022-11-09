package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.stardb.entity.CashbackOrderPolicyEntity
import com.ebaykorea.payback.util.PaybackTimestamps

import java.sql.Timestamp

class CashbackEntityGrocery {
  static def CashbackOrderEntity_생성(Map map = [:]) {
    new CashbackOrderEntity().tap {
      orderNo = (map.buyOrderNo ?: 1L) as long
      cashbackType = (map.cashbackType ?: "I") as String
      tradeCd = (map.tradeCd ?: "SV") as String
      amount = (map.amount ?: 1000L) as BigDecimal
      basisAmount = (map.basisAmount ?: 1000L) as BigDecimal
      itemNo = (map.itemNo ?: "itemNo1") as String
      packNo = (map.packNo ?: 1L) as long
      buyerNo = (map.buyerNo ?: "buyerNo") as String
      userKey = (map.userKey ?: "") as String
      tradeStatus = (map.tradeStatus ?: "10") as String
      useEnableDt = (map.useEnableDt ?: PaybackTimestamps.from(TestConstant.USE_ENABLE_DATE)) as Timestamp
      siteType = (map.siteType ?: "G") as String
      smileClubYn = (map.smileClubYn ?: "N") as String
      shopType = (map.shopType ?: null) as String
      regId = (map.regId ?: "buyerNo") as String
      regDt = (map.regDt ?: PaybackTimestamps.from(TestConstant.ORDER_DATE)) as Timestamp
      chgId = (map.chgId ?: "buyerNo") as String
    }
  }

  static def CashbackOrderPolicyEntity_생성(Map map = [:]) {
    new CashbackOrderPolicyEntity().tap {
      orderNo = (map.orderNo ?: 1L) as long
      type = (map.type ?: "I") as String
      policyNo = (map.policyNo ?: 0L) as Long
      name = (map.name ?: "cashbackTitle") as String
      subType = (map.subType ?: null) as String
      saveRate = (map.saveRate ?: 0L) as BigDecimal
      payType = (map.payType ?: "P") as String
      maxLimitMoney = map.maxLimitMoney as BigDecimal
      regId = (map.regId ?: "buyerNo") as String
      regDt = (map.regDt ?: PaybackTimestamps.from(TestConstant.ORDER_DATE)) as Timestamp
      chargePaySaveRate = (map.chargePaySaveRate ?: null) as BigDecimal
      chargePayClubSaveRate = (map.chargePayClubSaveRate ?: null) as BigDecimal
      chargePayMaxMoney = (map.chargePayMaxMoney ?: null) as BigDecimal
      chargePayClubMaxMoney = (map.chargePayClubMaxMoney ?: null) as BigDecimal
      clubDayMaxSaveRate = (map.clubDayMaxSaveRate ?: null) as BigDecimal
      clubDayMaxSaveMoney = (map.clubDayMaxSaveMoney ?: null) as BigDecimal
    }
  }
}
