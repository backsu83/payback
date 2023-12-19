package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.constant.TestConstant
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventResultEntity

import java.sql.Timestamp

class SmileCashEventEntityGrocery {
  static def SmileCashEventRequestEntity_생성(Map map = [:]) {
    new SmileCashEventRequestEntity().tap {
      requestMoney = (map.requestMoney ?: 0) as BigDecimal
      requestOutputDisabledMoney = (map.requestOutputDisabledMoney ?: 0) as BigDecimal
      cashBalanceType = (map.cashBalanceType ?: "") as String
      smilecashCode = (map.smilecashCode ?: "") as String
      custNo = (map.custNo ?: "custNo") as String
      expireDate = (map.expireDate ?: Timestamp.from(TestConstant.ORDER_DATE)) as Timestamp
      refNo = (map.refNo ?: 0L) as long
      ersNo = (map.ersNo ?: 0) as int
      regId = (map.regId ?: "") as String
    }
  }

  static def SmileCashEventResultEntity_생성(Map map = [:]) {
    new SmileCashEventResultEntity().tap{
      result = (map.result ?: 0) as int
      comment = (map.comment ?: "") as String
      smilePayNo = (map.smilePayNo ?: 0L) as long
    }
  }

  static def SmileCashEventEntity_생성(Map map = [:]) {
    new SmileCashEventEntity().tap{
      smilePayNo = (map.smilePayNo ?: 1L) as long
      status = (map.status ?: 0) as int
      returnCode = (map.returnCode ?: null) as String
    }
  }
}
