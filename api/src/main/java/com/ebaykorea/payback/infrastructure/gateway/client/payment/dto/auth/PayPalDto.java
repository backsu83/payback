package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayPalDto {
    String token;
    String business;
    String buyerNo;
    String countryCode;
    String currencyCode;
    String email;
    int exchangeNo;
    String firstName;
    String lastName;
    String merchantId;
    String middleName;
    BigDecimal originPrice;
    String payerId;
    String payerStatus;
    String paymentAction;
    long payPalNo;
    BigDecimal price;
    String salutation;
    String suffix;
}
