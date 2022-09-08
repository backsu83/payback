package com.ebaykorea.payback.api.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackResponse {

    private Integer totalItemCashbackAmount;
    private Integer totalNSPCashbackAmount;
    private Integer ifSmileCardCashbackAmount;
    private Integer ifNewSmileCardCashbackAmount;
    private String useEnableDate;
    /** 상품리스트 */
//    private List<Object> goods;
}
