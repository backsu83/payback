package com.ebaykorea.payback.api.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CashbackResponseDto {
  private String orderKey;
  private String txKey;
}
