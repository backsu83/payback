package com.ebaykorea.payback.schedulercluster.grocery

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.SmileCashAuthDto
import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent
import static com.ebaykorea.payback.schedulercluster.grocery.MassSaveRequestGrocery.SmileCashAuthDto_생성

class MassSaveEventGrocery {
  static def MassSaveEvent_생성(Map map = [:]) {
    new MassSaveEvent().tap {
      memberKey = (map.memberKey ?: "1") as String
      shopTransactionId = (map.shopTransactionId ?: "1") as String
      shopId = (map.shopId ?: null) as String
      subShopId = (map.subShopId ?: "") as String
      shopOrderId = (map.shopOrderId ?: "") as String
      promotionId = (map.promotionId ?: "") as String
      operator = (map.operator ?: "") as String
      retryCount = (map.retryCount ?: 0) as Integer
      seqNo = (map.seqNo ?: 0) as Integer
      status = (map.status ?: 0) as Integer
      smileCash = (map.smileCash ?: SmileCashAuthDto_생성(map)) as SmileCashAuthDto
    }
  }

}
