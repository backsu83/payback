package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.payment.Payment;
import com.ebaykorea.payback.core.gateway.PaymentGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.payment.PaymentApiClient;
import com.ebaykorea.payback.infrastructure.gateway.mapper.PaymentGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {
    private final PaymentApiClient paymentApiClient;
    private final PaymentGatewayMapper paymentGatewayMapper;

    @Override
    public Payment getPaymentRecord(Long paySeq) {
        return paymentGatewayMapper.map(paymentApiClient.findPaymentRecord(paySeq));
    }
}
