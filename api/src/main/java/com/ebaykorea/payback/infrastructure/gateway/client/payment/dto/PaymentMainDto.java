package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto;

import com.ebaykorea.payback.util.PaybackDecimals;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMainDto {
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
    /**
     * 결제 인증 seq
     */
//    long payAuthSeq;

    @JsonIgnore
    public boolean hasAmount() {
        return PaybackDecimals.isGreaterThanZero(amount);
    }

    @JsonIgnore
    public boolean hasMediumCode(final String ...mediumCodes) {
        for (String code : mediumCodes) {
            if (code.equals(mediumCode)) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean hasSmallCode(final String ...smallCodes) {
        for (String code : smallCodes) {
            if (code.equals(smallCode)) {
                return true;
            }
        }
        return false;
    }
}
