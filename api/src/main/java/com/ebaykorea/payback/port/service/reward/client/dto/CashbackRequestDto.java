package com.ebaykorea.payback.port.service.reward.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackRequestDto {

  private Integer totalPrice;
  private List<Goods> goods;

  @Getter
  @Setter
  @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
  public static class Goods {
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
}