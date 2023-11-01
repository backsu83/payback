package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.ShopType
import com.ebaykorea.payback.core.domain.constant.SmileCardType
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardCashback
import com.ebaykorea.payback.core.domain.entity.cashback.smilecard.SmileCardAdditionalCashback

class SmileCardCashbackGrocery {
  static def SmileCardCashback_생성(Map map = [:]) {
    SmileCardCashback.of(
        (map.cashbackAmount ?: 0L) as BigDecimal,
        (map.smileCardType ?: SmileCardType.Unknown) as SmileCardType,
        (map.isFreeInstallment ?: false) as boolean,
        (map.additionalCashbacks ?: []) as List<SmileCardAdditionalCashback>
    )
  }
  static def SmileCardAdditionalCashback_생성(Map map = [:]) {
    SmileCardAdditionalCashback.of(
        (map.orderNo ?: 1L) as long,
        (map.shopType ?: ShopType.Unknown) as ShopType,
        (map.amount ?: 0L) as BigDecimal,
        (map.basisAmount ?: 1000L) as BigDecimal,
        (map.smileCardType ?: SmileCardType.Unknown) as SmileCardType,
        (map.isFreeInstallment ?: false) as boolean
    )
  }

}
