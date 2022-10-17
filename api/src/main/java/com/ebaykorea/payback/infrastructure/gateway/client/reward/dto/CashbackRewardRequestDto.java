package com.ebaykorea.payback.infrastructure.gateway.client.reward.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class CashbackRewardRequestDto {

  private Integer totalPrice;
  private List<Goods> goods;

  @Data
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