package com.ebaykorea.payback.infrastructure.gateway.client.payment.dto.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class AlipayDto {
    String gmarketTradeNo;
}
