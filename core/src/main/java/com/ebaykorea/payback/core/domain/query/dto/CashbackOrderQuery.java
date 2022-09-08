package com.ebaykorea.payback.core.domain.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackOrderQuery {
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
    private String useEnableDate;
}
