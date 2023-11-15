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
  @Schema(description = "적립 요청 스마일페이 번호")
  private String smilePayNo;

  @Schema(description = "적립 처리 결과", example = "SUCCESS, FAILED, ALREADY_PROCESSED")
  private String resultCode;

  @Schema(description = "적립 처리 실패 사유")
  private String resultMessage;
}
