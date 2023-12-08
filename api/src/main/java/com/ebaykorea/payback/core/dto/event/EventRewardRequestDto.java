package com.ebaykorea.payback.core.dto.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRewardRequestDto {
  @Schema(description = "적립 요청 번호", required = true)
  private long requestNo;
  @Schema(description = "회원 키", required = true)
  private String memberKey;
  @Schema(description = "적립 금액", required = true)
  private BigDecimal saveAmount;
  @Schema(description = "이벤트 타입", example = "DailyCheckIn", required = true)
  private EventType eventType;
  @Schema(description = "만료 일자")
  private Instant expirationDate;
  @Schema(description = "적립 추가 사유")
  private String comment;
}
