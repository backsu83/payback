package com.ebaykorea.payback.core.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelSsgPointRequestDto {

  @Schema(description = "사이트타입" , defaultValue = "G")
  @NotNull
  private OrderSiteType siteType;

  @Schema(description = "주문번호")
  @NotNull
  private Long orderNo;
}
