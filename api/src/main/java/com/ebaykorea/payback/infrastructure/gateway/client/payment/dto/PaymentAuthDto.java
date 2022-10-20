package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto;

import com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth.CardDto;
import com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth.SmilePayDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAuthDto {

    SmilePayDto smilePay;
    CardDto card;

    @JsonIgnore
    public boolean isFreeInstallment() {
        return isSmilePayFreeInstallment() || isCardFreeInstallment();
    }

    @JsonIgnore
    private boolean isSmilePayFreeInstallment() {
        return Optional.ofNullable(smilePay)
                .map(SmilePayDto::getIsFreeInstallment)
                .orElse(false);
    }

    @JsonIgnore
    private boolean isCardFreeInstallment() {
        return Optional.ofNullable(card)
                .map(CardDto::isFreeInstallment)
                .orElse(false);
    }
}
