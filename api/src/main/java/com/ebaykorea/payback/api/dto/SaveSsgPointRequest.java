package com.ebaykorea.payback.api.dto;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveSsgPointRequest {

  @Schema(description = "사이트타입" , defaultValue = "G")
  @NotNull
  private OrderSiteType siteType;

  @Schema(description = "장바구니번호")
  @NotNull
  private Long packNo;

  @Schema(description = "구매자 아이디")
  @NotBlank
  private String buyerId;

  @Schema(description = "주문번호")
  @NotNull
  private Long orderNo;

  @Schema(description = "상품금액")
  @NotNull
  private BigDecimal payAmount;

  @Schema(description = "주문일" , format = "yyyy-MM-dd HH:i:s")
  @NotBlank
  private String orderDate;

  @Schema(description = "포인트 적립예정일" , format = "yyyy-MM-dd HH:i:s")
  @NotBlank
  private String scheduleDate;
}
