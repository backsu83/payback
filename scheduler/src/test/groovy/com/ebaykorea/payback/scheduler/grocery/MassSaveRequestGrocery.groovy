package com.ebaykorea.payback.scheduler.grocery

import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashAuthDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashResponseDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashResultBase
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity

import java.sql.Timestamp

class MassSaveRequestGrocery {
  static def SmileCashSaveQueueEntity_생성(Map map = [:]) {
    new SmileCashSaveQueueEntity().tap {
      seqNo = (map.seqNo ?: 1L) as Long
      txId = (map.txId ?: 1L) as Long
      memberId = (map.memberId ?: "memberId") as String
      reasonCode = (map.reasonCode ?: "") as String
      reasonComment = (map.reasonComment ?: "") as String
      bizKey = (map.bizKey ?: "") as String
      smileCashType = (map.smileCashType ?: 1) as int
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      saveStatus = (map.saveStatus ?: 3) as int
      expireDate = (map.expireDate ?: Timestamp.valueOf("2024-01-01 00:00:00")) as Timestamp
      retryCount = (map.retryCount ?: 0) as int
      insertOperator = (map.insertOperator ?: "insertOperator") as String
    }
  }

  static def SmileCashResponseDto_생성(Map map = [:]) {
    new SmileCashResponseDto().tap {
      resultBase = (map.resultBase ?: SmileCashResultBase_생성(map)) as SmileCashResultBase
    }
  }

  static def SmileCashResultBase_생성(Map map = [:]) {
    new SmileCashResultBase().tap {
      returnCode = (map.returnCode ?: "0000") as String
      errorMessage = (map.errorMessage ?: "") as String
    }
  }

  static def MassSaveRequestDto_생성(Map map = [:]) {
    new MassSaveRequestDto().tap {
      shopTransactionId = (map.shopTransactionId ?: "1") as String
      shopId = (map.shopId ?: "S002") as String
      subShopId = (map.subShopId ?: "IAC") as String
      shopOrderId = (map.shopOrderId ?: "") as String
      promotionId = (map.promotionId ?: "GPR0002") as String
      smileCash = (map.smileCash ?: SmileCashAuthDto_생성(map)) as SmileCashAuthDto
    }
  }

  static def SmileCashAuthDto_생성(Map map = [:]) {
    new SmileCashAuthDto().tap {
      cashCode = (map.cashCode ?: "3") as String
      amount = (map.amount ?: 0) as BigDecimal
      shopManageCode = (map.shopManageCode ?: "") as String
      shopComment = (map.shopComment ?: "") as String
      expirationDate = (map.expirationDate ?: "2024-01-01 00:00:00") as String
    }
  }
}
