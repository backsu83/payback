package com.ebaykorea.payback.grocery

import com.ebaykorea.payback.core.domain.constant.PaymentType
import com.ebaykorea.payback.core.domain.entity.payment.Payment
import com.ebaykorea.payback.core.domain.entity.payment.PaymentMethod
import com.ebaykorea.payback.core.domain.entity.payment.PaymentMethodSub
import com.ebaykorea.payback.core.domain.entity.payment.SmilePay

class PaymentGrocery {
    static def payment_생성(Map map = [:]) {
        new Payment(
                (map.paymentSequence ?: "38876501") as Long ,
                (map.txKey ?: "15d399b007000300ftlsxgk") as String ,
                (map.buyerNo ?: "132872352") as String ,
                (map.buyerId ?: "seunghbaek") as String ,
                (map.partnershipCode ?: "200011415") as String ,
                (map.mainPaymentMethod ?: paymentMethod_생성()) as PaymentMethod ,
                (map.subPaymentMethods ?: []) as List<PaymentMethodSub> ,
                (map.smilePay ?: smilePay_생성() as SmilePay),
                (map.mainPaymentType ?: PaymentType.NewSmilePay as PaymentType)
        )
    }

    static def paymentMethod_생성(Map map = [:]) {
        new PaymentMethod(
                (map.amount ?: 17000L) as BigDecimal,
                (map.mediumCode ?: "200000036") as String,
                (map.smallCode ?: "300000290") as String,
        )
    }

    static def smilePay_생성(Map map = [:]) {
        new SmilePay(
                (map.certificationId ?: "1100000017276736S001" as String),
                (map.smilePayToken ?: "token" as String),
                (map.totalMoney ?: 17000L as BigDecimal),
                (map.cardRequestMoney ?: 0L as BigDecimal),
                (map.cashRequestMoney ?: 0L as BigDecimal),
                (map.mobileRequestMoney ?: 0L as BigDecimal),
                (map.etcRequestMoney ?: 0L as BigDecimal),
                (map.ePrepayRequestMoney ?: 0L as BigDecimal),
                (map.isFreeInstallment ?: false as Boolean),
                (map.settleGroupSequence ?: null as Long),
                (map.smilePayContractCode ?: [100] as List<Long>),
                (map.smilePayItemType ?: 1 as Integer)
        )
    }


}
