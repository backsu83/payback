package com.ebaykorea.payback.core.dto.cashback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCashbackRequestDto {
  @NotEmpty
  private String txKey;
  @NotEmpty
  private String orderKey;
}
