package com.ebaykorea.payback.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CashbackResponseDto {
  private String orderKey;
  private String txKey;

  public static CashbackResponseDto of(String orderKey , String txKey) {
    return new CashbackResponseDto(orderKey , txKey);
  }
}
