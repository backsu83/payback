package com.ebaykorea.payback.core.dto.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import org.checkerframework.checker.index.qual.Positive;

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

  @Schema(description = "예산 할당 번호")
  private Long budgetNo;

  @Schema(description = "이벤트 번호")
  private Long eventNo;

  @Schema(description = "만료 일자")
  private Instant expirationDate;
}
