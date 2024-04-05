package com.ebaykorea.payback.schedulercluster.grocery

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.*

class MassSaveRequestGrocery {

  static def SmileCashResponseDto_생성(Map map = [:]) {
    new MassSaveResponseDto().tap {
      resultBase = (map.resultBase ?: SmileCashResultBase_생성(map)) as SmileCashResultBase
    }
  }

  static def MassSaveResponseDto_생성(Map map = [:]) {
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
      shopId = (map.shopId ?: null) as String
      subShopId = (map.subShopId ?: "") as String
      shopOrderId = (map.shopOrderId ?: "") as String
      promotionId = (map.promotionId ?: "") as String
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
}
