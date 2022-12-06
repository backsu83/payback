package com.ebaykorea.payback.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCashbackRequestDto {
  private String txKey;
  private String orderKey;
}
