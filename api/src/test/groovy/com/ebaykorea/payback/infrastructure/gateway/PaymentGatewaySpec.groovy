package com.ebaykorea.payback.infrastructure.gateway

import com.ebaykorea.payback.infrastructure.gateway.client.payment.PaymentApiClient
import com.ebaykorea.payback.infrastructure.mapper.PaymentGatewayMapper
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.ebaykorea.payback.grocery.PaymentApiGrocery.paymentDto_생성
import static com.ebaykorea.payback.grocery.PaymentGrocery.payment_생성

class PaymentGatewaySpec extends Specification {
    def paymentApiClient = Stub(PaymentApiClient)
    def paymentGatewayMapper = Mappers.getMapper(PaymentGatewayMapper)
    def paymentGatewayImpl = new PaymentGatewayImpl(paymentApiClient , paymentGatewayMapper)

    def "Payment 변환 확인"() {
        setup:
        paymentApiClient.findPaymentRecord(_ as Long) >> response

        expect:
        def result = paymentGatewayImpl.findPaymentRecord(38876501L)
        result == expectResult

        where:
        desc | response | expectResult
        "Dto 변환"  | paymentDto_생성() | payment_생성()
    }
}
