package com.ebaykorea.payback.core.exception;

import lombok.Getter;

@Getter
public class PaybackException extends RuntimeException{
    private PaybackExceptionCode code;
    private String message;

    public PaybackException(String message) {
        super(message);
    }

    public PaybackException(PaybackExceptionCode code ,String message) {
        this.code = code;
        this.message = message;
    }
}
