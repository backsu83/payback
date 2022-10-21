package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.infrastructure.gateway.client.transaction.dto.KeyMapDto

class TransactionApiGrocery {
  static def KeyMapDto_생성(Map map = [:]) {
    new KeyMapDto().tap {
      transactionKey = (map.transactionKey ?: "transactionKey") as String
      orderKey = (map.orderKey ?: "orderKey") as String
      packNo = (map.packNo ?: 1L) as Long
      orderUnitKey = (map.orderUnitKey ?: "orderUnitKey1") as String
      orderNo = (map.orderNo ?: 1L) as Long
      contrNo = (map.contrNo ?: 1L) as Long
    }
  }
}
