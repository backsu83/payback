package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointVerifyRequest {
    private String clientId;
    private String apiKey;
    private String tokenId;
    private String reqTrcNo;
    private String reqDate;
    private Long sumCount;
    private BigDecimal sumAmt;
    private String tradeType;
    private String brchId;
}
