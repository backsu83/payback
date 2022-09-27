package com.ebaykorea.payback.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCashbackRequestDto {
  private String orderKey;
  private String txKey;
}
