package com.ebaykorea.payback.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackOrderResponse {
    private long buyOrderNo;
    private String cashbackType;
    private String tradeCd;
    private BigDecimal cashbackMoney;
    private BigDecimal cashbackBasisMoney;
    private String gdNo;
    private long packNo;
    private String custNo;
    private String userKey;
    private String tradeStatus;
    private Instant useEnableDt;
    private String siteType;
    private Long cashbackReqSeq;
    private Boolean smileClubYn;
    private String shopType;
    private Boolean isRequestCancel;

    private Integer totalItemCashbackAmount ;
    private Integer totalNSPCashbackAmount ;
    private Integer ifSmileCardCashbackAmount;
    private Integer ifNewSmileCardCashbackAmount;
    private String useEnableDate ;
}
