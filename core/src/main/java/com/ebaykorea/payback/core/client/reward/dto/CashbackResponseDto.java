package com.ebaykorea.payback.core.client.reward.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackResponseDto {
    private Integer totalItemCashbackAmount ;
    private Integer totalNSPCashbackAmount ;
    private Integer ifSmileCardCashbackAmount;
    private Integer ifNewSmileCardCashbackAmount;
    private String useEnableDate;
    private List<Object> goods;
}
