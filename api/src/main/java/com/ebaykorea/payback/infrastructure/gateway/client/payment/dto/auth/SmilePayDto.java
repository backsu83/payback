package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    BigDecimal ePrepayRequestMoney;
    Boolean isFreeInstallment;
    Long settleGroupSequence;
    List<Long> smilePayContractCode;
    Integer smilePayItemType;
}
