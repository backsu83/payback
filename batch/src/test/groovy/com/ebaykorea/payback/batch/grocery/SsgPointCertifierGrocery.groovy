package com.ebaykorea.payback.batch.grocery

import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties
import com.ebaykorea.payback.batch.domain.SsgPointCertifier
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType

class SsgPointCertifierGrocery {

  static def SsgPointCertifier_생성(Map map = [:]) {
    SsgPointCertifier.of(
            SsgPointAuthProperties_생성(),
            (map.orderSiteType ?: OrderSiteType.Gmarket) as OrderSiteType
    )
  }

  static def SsgPointAuthProperties_생성(Map map = [:]) {
    new SsgPointAuthProperties(
            (map.gmarket ?: Gmarket_생성()) as SsgPointAuthProperties.Gmarket,
            (map.auction ?: Auction_생성()) as SsgPointAuthProperties.Auction
    )
  }

  static def Gmarket_생성(Map map = [:]) {
    new SsgPointAuthProperties.Gmarket(
            (map.memberKey ?: "S001") as String,
            (map.clientId ?: "49E615F309BC23C5CA7E4603E2036977") as String,
            (map.apiKey ?: "E320844B8E294F3E8D69395737C8B194") as String,
            (map.encryptKey ?: "831A667A8A3015F85FF5824DCDFD4C58") as String,
            (map.encryptIv ?: "7552B56514CCA47A") as String,
            (map.branchId ?: "B200042500") as String
    )
  }

  static def Auction_생성(Map map = [:]) {
    new SsgPointAuthProperties.Auction(
            (map.memberKey ?: "S002") as String,
            (map.clientId ?: "3C24A0D1FADA47F07F9A79D30D4C9A2E") as String,
            (map.apiKey ?: "72787780CCA9F00A5D584991826752E2") as String,
            (map.encryptKey ?: "5D459675AEE24BEA36FF2B1B661B2F67") as String,
            (map.encryptIv ?: "73DDDFC9C36D49CB") as String,
            (map.branchId ?: "B200042502") as String
    )
  }
}
