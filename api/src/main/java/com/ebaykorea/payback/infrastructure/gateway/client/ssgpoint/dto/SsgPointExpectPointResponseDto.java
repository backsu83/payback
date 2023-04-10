package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SsgPointExpectPointResponseDto {
    private String clientId;
    private String apiKey;
    private String responseCd;
    private String responseMsg;
    private BigDecimal gpoint;
}
