package com.ebaykorea.payback.core.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRewardResponseDto {
  @Schema(description = "요청 아이디")
  private String requestId;

  @Schema(description = "적립 처리 식별 아이디")
  private String saveProcessId;

  @Schema(description = "적립 처리 유형", example = "SMILE_CASH")
  private String saveProcessType;

  @Schema(description = "적립 처리 결과", example = "SUCCESS, FAILED, ALREADY_PROCESSED")
  private String resultCode;
}
