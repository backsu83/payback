package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    Boolean isFreeInstallment;
    Long settleGroupSequence;
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
