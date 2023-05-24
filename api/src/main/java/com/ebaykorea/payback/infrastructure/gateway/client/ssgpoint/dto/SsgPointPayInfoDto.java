package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SsgPointPayInfoDto {
    private String payType;
    private BigDecimal payAmt;
    private String payGb;
}
