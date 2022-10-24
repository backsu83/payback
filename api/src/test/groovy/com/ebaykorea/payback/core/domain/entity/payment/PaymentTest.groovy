package com.ebaykorea.payback.core.domain.entity.payment


import com.ebaykorea.payback.core.domain.constant.PaymentType
import spock.lang.Specification

import static com.ebaykorea.payback.core.domain.constant.PaymentCode.PaymentMethodMediumCode.*
import static com.ebaykorea.payback.grocery.PaymentGrocery.payment_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.paymentMethod_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.smilePay_생성

class PaymentTest extends Specification {


    def "대표결제수단을 코드로 분류"() {
        setup:
        def paymentMethod = paymentMethod_생성(mediumCode : code)

        expect:
        def result = PaymentType.ofMediumCode(code)
        code == codeName
        result == paymentMethod.toMainPaymentType(code , 1L)
        result == 결과

        where: "코드 분류"
        desc             | code        | codeName          | 결과
        "복합결제: AX"   | "000000000" | Complex           | PaymentType.Complex
        "알리페이: IA"   | "200000028" | AliPay            | PaymentType.AliPay
        "스마일페이: SP" | "200000035" | NewSmilePayCard   | PaymentType.NewSmilePay
        "스마일페이: SP" | "200000036" | NewSmilePayCMS    | PaymentType.NewSmilePay
        "스마일페이: SP" | "200000040" | NewSmilePayMobile | PaymentType.NewSmilePay
    }

    def "대표결제수단을 복잡결제로 변경"() {
        setup:
        def payment = payment_생성(mainPaymentType : type , subPaymentMethods : subPayment)

        expect:
        def result = payment.toPaymentType()
        result == 결과

        where:
        desc                                                    | type                | subPayment            | 결과
        "부결제타입이 있는 경우 무조건 복합결제"                | PaymentType.Unknown | [PaymentType.Card]    | PaymentType.Complex
        "부결제타입이 없고 결제타입이 Complex(복합결제)인 경우" | PaymentType.Complex | []                    | PaymentType.Complex
        "부결제타입이 없고 결제타입이 NewSmilePay 인 경우"      | PaymentType.NewSmilePay | []                | PaymentType.Complex
        "부결제타입이 없고 결제타입이 NewSmilePay 인 아닌 경우" | PaymentType.AliPay | []                     | PaymentType.AliPay
    }

    def "금액으로 스마일페이상세 분류"() {
        setup:
        def smilePay = smilePay_생성(
                cardRequestMoney: card,
                cashRequestMoney: cash,
                mobileRequestMoney: mobile,
                etcRequestMoney: etc,
                ePrepayRequestMoney: ePrepay,
        )

        expect:
        def types = smilePay.toSmilepayPaymentTypes()
        types == 결과

        where:
        desc              | card | cash | mobile | etc |  ePrepay | 결과
        "스마일페이 SR"   | 10L  | 0L   | 0L     | 0L  | 0L          | [PaymentType.NewSmilePayCard]
        "스마일페이 SH"   | 0L   | 10L  | 0L     | 0L  | 0L          | [PaymentType.NewSmilePayCash]
        "스마일페이 SM"   | 0L   | 0L   | 10L    | 0L  | 0L          | [PaymentType.NewSmilePayMobile]
        "스마일페이 SE"   | 0L   | 0L   | 0L     | 10L | 0L          | [PaymentType.NewSmilePayEtc]
        "스마일페이 SG"   | 0L   | 0L   | 0L     | 0L  | 10L         | [PaymentType.NewSmilePayEPrePay]
    }


}
