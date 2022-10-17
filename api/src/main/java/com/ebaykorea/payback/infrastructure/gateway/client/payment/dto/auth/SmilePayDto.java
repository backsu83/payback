package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class SmilePayDto {

    String certificationId;
    String smilePayToken;
    BigDecimal totalMoney;
    BigDecimal cardRequestMoney;
    BigDecimal cashRequestMoney;
    BigDecimal mobileRequestMoney;
    BigDecimal etcRequestMoney;
    @JsonProperty("ePrepayRequestMoney")
    BigDecimal ePrepayRequestMoney;
    String cashReceiptEncryptionValue;
//    CashReceiptType cashReceiptType;
//    CashReceiptWayType cashReceiptWayType;
    Boolean isFreeInstallment;
    Long settleGroupSeq;
    List<Long> smilePayContractCode;
    Integer smilePayItemType;

    @JsonIgnore
    public Optional<BigDecimal> findCardRequestMoney(){
        return Optional.ofNullable(cardRequestMoney);
    }

    @JsonIgnore
    public Optional<BigDecimal> findCashRequestMoney(){
        return Optional.ofNullable(cashRequestMoney);
    }

    @JsonIgnore
    public Optional<BigDecimal> findMobileRequestMoney(){
        return Optional.ofNullable(mobileRequestMoney);
    }

    @JsonIgnore
    public Optional<BigDecimal> findEtcRequestMoney(){
        return Optional.ofNullable(etcRequestMoney);
    }

    @JsonIgnore
    public Optional<BigDecimal> findEPrepayRequestMoney(){
        return Optional.ofNullable(ePrepayRequestMoney);
    }
}
