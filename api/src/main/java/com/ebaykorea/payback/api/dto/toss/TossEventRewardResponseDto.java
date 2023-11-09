package com.ebaykorea.payback.api.dto.toss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossEventRewardResponseDto {
  @Schema(description = "적립 처리 식별 아이디")
  private String transactionId;

  @Schema(description = "적립 처리 결과 코드", example = "SUCCESS, FAILED, ALREADY_PROCESSED, IN_PROGRESS")
  private String resultCode;

  @Schema(description = "적립 처리 실패 사유")
  private String resultMessage;
}
