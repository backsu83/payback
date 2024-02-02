package com.ebaykorea.payback.api.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossEventRewardRequestDto {
  @Schema(description = "적립 요청 번호", required = true)
  private long requestNo;

  @Schema(description = "회원 키", required = true)
  private String memberKey;

  @Schema(description = "적립 금액", required = true)
  private BigDecimal saveAmount;
}
