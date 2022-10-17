package com.ebaykorea.payback.infrastructure.gateway.client.payment

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PaymentApiClientTest extends Specification {

    @Autowired
    PaymentApiClient paymentApiClient;

    def "findPaymentRecord"() {
        setup:
        def paySed = Long.valueOf(38876501);

        expect:
        def result = paymentApiClient.findPaymentRecord(paySed);
        println result;

    }
}
