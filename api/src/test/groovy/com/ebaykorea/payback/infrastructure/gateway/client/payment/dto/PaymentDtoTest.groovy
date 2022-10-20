package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto


import com.ebaykorea.payback.core.domain.constant.PaymentType
import spock.lang.Specification

import static com.ebaykorea.payback.core.domain.constant.PaymentCode.PaymentMethodMediumCode.*
import static com.ebaykorea.payback.grocery.PaymentApiGrocery.paymentDto_생성
import static com.ebaykorea.payback.grocery.PaymentApiGrocery.smilePayDto_생성

class PaymentDtoTest extends Specification {

    def "대표결제수단 검증 조건"() {
        setup:
        def paymentDto = paymentDto_생성(amount : paymentAmount)

        expect:
        def condition1 = paymentDto.hasMainPayment()
        def condition2 = paymentDto.hasMainPaymentMethod(paymentDto.mainPaymentMediumCode(Direct))
        결과 == (condition1 || condition2)

        where:
        desc              | paymentAmount    | 결과
        "결제금액 == 0"   | "0"              | false
        "결제금액 > 0"    | "1700.0000"      | true

    }

    def "대표결제수단을 코드로 분류"() {
        setup:
        def paymentDto = paymentDto_생성(mediumCode : code)

        expect:
        def result = PaymentType.ofMediumCode(code)
        code == codeName
        result == paymentDto.toRawMainPaymentType()
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
        def paymentDto = paymentDto_생성(mediumCode : code , subPaymentMethods : subPayment)

        expect:
        def result = paymentDto.toPaymentType()
        result == 결과

        where:
        desc                                                    | code        | subPayment            | 결과
        "부결제타입이 있는 경우 무조건 복합결제"                | "000000000" | [Stub(PaymentSubDto)] | PaymentType.Complex
        "부결제타입이 없고 결제타입이 Complex(복합결제)인 경우" | "000000000" | []                    | PaymentType.Complex
        "부결제타입이 없고 결제타입이 NewSmilePay 인 경우"      | "200000035" | []                    | PaymentType.Complex
        "부결제타입이 없고 결제타입이 NewSmilePay 인 아닌 경우" | "200000028" | []                    | PaymentType.AliPay
    }

    def "금액으로 스마일페이상세 분류"() {
        setup:
        def paymentDto = paymentDto_생성(mediumCode : code , smilePay : smilePayDto_생성(
                cardRequestMoney: card,
                cashRequestMoney: cash,
                mobileRequestMoney: mobile,
                etcRequestMoney: etc,
                ePrepayRequestMoney: ePrepay,
        ))

        expect:
        def types = paymentDto.toDetailedMainPaymentTypes()
        types == 결과

        where:
        desc              | code        | card | cash | mobile | etc |  ePrepay | 결과
        "스마일페이 SR"   | "200000035" | 10L  | 0L   | 0L     | 0L  | 0L          | [PaymentType.NewSmilePayCard]
        "스마일페이 SH"   | "200000035" | 0L   | 10L  | 0L     | 0L  | 0L          | [PaymentType.NewSmilePayCash]
        "스마일페이 SM"   | "200000035" | 0L   | 0L   | 10L    | 0L  | 0L          | [PaymentType.NewSmilePayMobile]
        "스마일페이 SE"   | "200000035" | 0L   | 0L   | 0L     | 10L | 0L          | [PaymentType.NewSmilePayEtc]
        "스마일페이 SG"   | "200000035" | 0L   | 0L   | 0L     | 0L  | 10L         | [PaymentType.NewSmilePayEPrePay]
    }


}
