package com.ebaykorea.payback.core.dto.event;

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
public class MemberEventRewardRequestDto {
  @Schema(description = "적립 요청 번호", required = true)
  private long requestNo;
  @Schema(description = "적립 금액", required = true)
  private BigDecimal saveAmount;
  @Schema(description = "이벤트 타입", example = "Toss", required = true)
  private EventType eventType;
}
