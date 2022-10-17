package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto;

import lombok.Getter;

@Getter
public class PaymentBusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public PaymentBusinessException(String message) {
        super(message);
    }

    public PaymentBusinessException(Integer code ,String message) {
        this.code = code;
        this.message = message;
    }
}
