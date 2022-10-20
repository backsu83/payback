package com.ebaykorea.payback.infrastructure.gateway.client.payment


import spock.lang.Specification
import static com.ebaykorea.payback.grocery.PaymentApiGrocery.paymentDto_생성


class PaymentApiClientTest extends Specification {
    def paymentClientApi = Stub(PaymentApiClient)


    def "findPaymentRecord"() {
        setup:
        paymentClientApi.findPaymentRecord(_ as Long) >> response

        expect:
        def result = paymentClientApi.findPaymentRecord(paySeq)
        result == response

        where:
        desc                   | paySeq                 | response
        "payment api 테스트"   | Long.valueOf(38876501) | paymentDto_생성()


    }
}
