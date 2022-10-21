package com.ebaykorea.payback.core.gateway;

import com.ebaykorea.payback.core.domain.entity.payment.Payment;

public interface PaymentGateway {

    Payment findPaymentRecord(Long paySeq);
}
