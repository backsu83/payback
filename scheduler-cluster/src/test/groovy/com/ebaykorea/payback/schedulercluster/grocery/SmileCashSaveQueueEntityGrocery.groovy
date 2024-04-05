package com.ebaykorea.payback.schedulercluster.grocery

import com.ebaykorea.payback.schedulercluster.model.constant.AuctionSmileCashEventType
import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.entity.SmileCashSaveQueueEntity

import java.sql.Timestamp

class SmileCashSaveQueueEntityGrocery {
  static def SmileCashSaveQueueEntity_생성(Map map = [:]) {
    new SmileCashSaveQueueEntity().tap {
      seqNo = (map.seqNo ?: 1L) as Long
      txId = (map.txId ?: 1L) as Long
      memberId = (map.memberId ?: "memberId") as String
      auctionSmileCashEventType = (map.auctionSmileCashEventType ?: AuctionSmileCashEventType.Unknown) as AuctionSmileCashEventType
      reasonComment = (map.reasonComment ?: "") as String
      additionalReasonComment = (map.additionalReasonComment ?: "") as String
      bizType = (map.bizType ?: 9) as int
      bizKey = (map.bizKey ?: "") as String
      smileCashType = (map.smileCashType ?: 1) as int
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      saveStatus = (map.saveStatus ?: 3) as int
      expireDate = (map.expireDate ?: Timestamp.valueOf("2024-01-01 00:00:00")) as Timestamp
      retryCount = (map.retryCount ?: 0) as int
      insertOperator = (map.insertOperator ?: "insertOperator") as String
    }
  }

}
