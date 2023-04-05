package com.ebaykorea.payback.core.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSsgPointTradeStatusRequestDto {
  @Schema(description = "주문번호")
  @NotNull
  private Long orderNo;
  @Schema(description = "구매자 아이디")
  @NotNull
  private String buyerId;

  @Schema(description = "사이트타입" , defaultValue = "G")
  @NotNull
  private OrderSiteType siteType;

  @Schema(description = "적립/취소")
  @NotNull
  private String tradeType;

  @Schema(description = "수동 처리 관리자 아이디")
  @NotNull
  private String adminId;
}
