package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class AlipayDto {
    String gmarketTradeNo;
}
