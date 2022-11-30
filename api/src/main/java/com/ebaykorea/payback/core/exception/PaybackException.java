package com.ebaykorea.payback.core.exception;

import com.ebaykorea.payback.core.domain.constant.PaybackMessageType;
import java.text.MessageFormat;
import lombok.Getter;

@Getter
public class PaybackException extends RuntimeException{
    private PaybackMessageType code;
    private String message;

    public PaybackException(String message) {
        super(message);
    }

    public PaybackException(PaybackMessageType paybackMessageType , String message) {
        this.code = paybackMessageType;
        this.message = MessageFormat.format(paybackMessageType.getMessage() , message);
    }

    public PaybackException(PaybackMessageType paybackMessageType) {
        this.code = paybackMessageType;
        this.message = paybackMessageType.getMessage();
    }
}
