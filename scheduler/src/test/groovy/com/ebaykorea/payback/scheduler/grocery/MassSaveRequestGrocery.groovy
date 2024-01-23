package com.ebaykorea.payback.scheduler.grocery

import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveRequestDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultResponseDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashAuthDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveResponseDto
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashResultBase
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashSaveResultDto
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveApprovalEntity
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

  static def SmileCashResponseDto_생성(Map map = [:]) {
    new MassSaveResponseDto().tap {
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

  static def SaveResultResponseDto_생성(Map map = [:]) {
    new SaveResultResponseDto().tap {
      result = (map.result ?: SaveResultDto_생성(map)) as SaveResultDto
      resultBase = (map.resultBase ?: SmileCashResultBase_생성(map)) as SmileCashResultBase
    }
  }

  static def SaveResultDto_생성(Map map = [:]) {
    new SaveResultDto().tap {
      authorizationId = (map.authorizationId ?: null) as String
      shopTransactionId = (map.shopTransactionId ?: null) as String
      smileCash = (map.smileCash ?: SmileCashSaveResultDto_생성(map)) as SmileCashSaveResultDto
    }
  }

  static def SmileCashSaveResultDto_생성(Map map = [:]) {
    new SmileCashSaveResultDto().tap {
      amount = (map.amount ?: null) as BigDecimal
      shopManageCode = (map.shopManageCode ?: null) as String
      shopComment = (map.shopComment ?: null) as String
      expirationDate = (map.expirationDate ?: null) as String
      transactionDate = (map.transactionDate ?: null) as String
    }
  }

  static def 성공_SmileCashSaveResultDto_생성(Map map = [:]) {
    new SmileCashSaveResultDto().tap {
      amount = (map.amount ?: 10) as BigDecimal
      shopManageCode = (map.shopManageCode ?: "RM04Y") as String
      shopComment = (map.shopComment ?: "구매후기 - 별점 평가 적립") as String
      expirationDate = (map.expirationDate ?: "") as String
      transactionDate = (map.transactionDate ?: "2024-01-18 21:11:20.123") as String
    }
  }

  static def SmileCashSaveApprovalEntity_생성(Map map = [:]) {
    new SmileCashSaveApprovalEntity().tap {
      txId = (map.txId ?: 0L) as long
      smileCashTxId = (map.smileCashTxId ?: "") as String
      smileUserKey = (map.smileUserKey ?: "") as String
      txnType = (map.txnType ?: 0) as int
      transactionDate = (map.transactionDate ?: null) as Timestamp
      smileCashType = (map.smileCashType ?: 0) as int
      saveAmount = (map.saveAmount ?: 0) as BigDecimal
      expireDate = (map.expireDate ?: null) as Timestamp
      diffProcBaseDate = (map.diffProcBaseDate ?: null) as Timestamp
      diffProcIs = (map.diffProcIs ?: 0) as int
      reasonCode = (map.reasonCode ?: "") as String
      reasonComment = (map.reasonComment ?: "") as String
      additionalReasonComment = (map.additionalReasonComment ?: "") as String
      bizType = (map.bizType ?: 0) as int
      bizKey = (map.bizKey ?: "") as String
      insertOperator = (map.insertOperator ?: "") as String
    }
  }
}
