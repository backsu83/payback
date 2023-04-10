package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SsgPointExpectPointRequestDto {
    private String clientId;
    private String apiKey;
    private String tokenId;
    private String reqTrcNo;
    private String brchId;
    private BigDecimal pntNoAddProdAmt;
    private BigDecimal totAmt;
    private List<SsgPointPayInfoDto> payinfo;
}
