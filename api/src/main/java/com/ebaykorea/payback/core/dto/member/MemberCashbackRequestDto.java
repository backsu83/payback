package com.ebaykorea.payback.core.dto.member;

import com.ebaykorea.payback.core.domain.constant.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCashbackRequestDto {
  @Schema(description = "결제번호", required = true)
  private long payNo;
  @Schema(description = "적립금액", required = true)
  private BigDecimal saveAmount;
  @Schema(description = "이벤트타입", example = "Normal or Toss", required = true)
  private EventType eventType;

}
