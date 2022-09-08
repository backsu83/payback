package com.ebaykorea.payback.core.domain.query.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CashbackRewardQuery {
    private Integer totalItemCashbackAmount ;
    private Integer totalNSPCashbackAmount ;
    private Integer ifSmileCardCashbackAmount;
    private Integer ifNewSmileCardCashbackAmount;
    private String useEnableDate;
}
