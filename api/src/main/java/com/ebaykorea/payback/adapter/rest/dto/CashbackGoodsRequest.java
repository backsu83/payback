package com.ebaykorea.payback.adapter.rest.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackGoodsRequest {
    private Integer siteCd;
    private String gdNo;
    private String gdlcCd;
    private String gdmcCd;
    private String gdscCd;
    private String scNo;
    private Boolean isSmileClub;
    private Boolean isSmileDelivery;
    private Integer qty;
    private Integer price;
}
