package com.ebaykorea.payback.core.exception;

import lombok.Getter;

@Getter
public class PaybackException extends RuntimeException{
    private Integer code;
    private String message;

    public PaybackException(String message) {
        super(message);
    }

    public PaybackException(Integer code ,String message) {
        this.code = code;
        this.message = message;
    }

    public PaybackException(PaybackExceptionCode code, String message) {
        this.code = code.ordinal();
        this.message = message;
    }
}
