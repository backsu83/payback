package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.gateway.PaymentGateway;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class PaymentGatewayImplTest {

    @Autowired
    PaymentGateway paymentGateway;

    @Test
    void findPaymentRecord() {
        paymentGateway.getPaymentRecord(38876501L);
    }
}