package com.ebaykorea.payback.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SaveCashbackRequestDto {
  @NotEmpty
  private String txKey;
  @NotEmpty
  private String orderKey;
}
