package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.ebaykorea.payback.core.domain.constant.InstallmentType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CardDto {

    InstallmentType freeInstallmentType;

    // 무이자할부 여부
    public boolean isFreeInstallment() {
        return freeInstallmentType == InstallmentType.InterestFree
                || freeInstallmentType == InstallmentType.InterestOnGmkt;
    }

}
