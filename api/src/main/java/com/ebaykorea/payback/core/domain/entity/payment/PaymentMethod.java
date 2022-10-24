package com.ebaykorea.payback.core.domain.entity.payment;

import com.ebaykorea.payback.core.domain.constant.PaymentType;
import com.ebaykorea.payback.util.PaybackDecimals;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class PaymentMethod {
    /**
     * 결제 금액
     */
    BigDecimal amount;
    /**
     * 중분류코드
     */
    String mediumCode;
    /**
     * 소분류코드
     */
    String smallCode;

    public static PaymentType toMainPaymentType(String mediumCode , BigDecimal amount) {
        // 부 결제 수단 100%인 경우 (스마일캐시 전액 등) 주 결제수단은 Unknown
        if(PaybackDecimals.isGreaterThanZero(amount)) {
            return PaymentType.ofMediumCode(mediumCode);
        } else {
            return PaymentType.Unknown;
        }
    }

}
