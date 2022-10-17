package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto;

import com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Optional;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class PaymentAuthDto {

    /**
     * 알리페이
     */
    AlipayDto alipay;
    /**
     * 신용카드
     */
    CardDto card;
    /**
     * 휴대폰 소액결제
     */
    CellPhoneDto cellPhone;
    /**
     * 스마일페이
     */
    SmilePayDto smilePay;
    /**
     * 페이팔
     */
    PayPalDto payPal;
    /**
     * 스마일캐시
     */
    SmileCashDto smileCash;
    /**
     * 가상계좌
     */
    WireTransferAccountDto wireTransfer;


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
