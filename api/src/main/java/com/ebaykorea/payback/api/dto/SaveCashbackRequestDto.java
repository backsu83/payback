package com.ebaykorea.payback.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SaveCashbackRequestDto {
  private String orderKey;
  private String txKey;
}
