package com.ebaykorea.payback.port.service.reward.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackResponseDto {
    Integer totalItemCashbackAmount ;
    Integer totalNSPCashbackAmount ;
    Integer ifSmileCardCashbackAmount;
    Integer ifNewSmileCardCashbackAmount;
    String useEnableDate;
    List<Object> goods;
}
