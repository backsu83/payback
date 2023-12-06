package com.ebaykorea.payback.core.dto.event.toss;

import com.ebaykorea.payback.core.domain.constant.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossEventRewardRequestDto {
  @Schema(description = "요청 아이디", required = true)
  @NotEmpty(message = "requestId cannot be empty")
  private String requestId;

  @Schema(description = "이벤트 타입", example = "Toss", required = true)
  @NotNull(message = "eventType cannot be null")
  private EventType eventType;

  @Schema(description = "유저 정보", required = true)
  @NotEmpty(message = "userToken cannot be empty")
  private String userToken;

  @Schema(description = "적립 금액", required = true)
  @NotNull(message = "saveAmount cannot be null")
  private BigDecimal saveAmount;

  @Schema(description = "적립 사유")
  private String message;

  @Schema(description = "이벤트 상세")
  private List<TossEventRewardRequestDetailDto> details;
}
