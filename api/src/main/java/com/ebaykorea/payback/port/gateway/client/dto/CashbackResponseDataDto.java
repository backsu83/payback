package com.ebaykorea.payback.port.gateway.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackResponseDataDto {
    Integer totalItemCashbackAmount ;
    Integer totalNSPCashbackAmount ;
    Integer ifSmileCardCashbackAmount;
    Integer ifNewSmileCardCashbackAmount;
    String useEnableDate;
    List<Object> goods;
}
