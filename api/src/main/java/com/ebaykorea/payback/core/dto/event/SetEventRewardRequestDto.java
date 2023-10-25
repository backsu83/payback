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
public class SetEventRewardRequestDto {
  @Schema(description = "변경할 승인 상태", required = true, example = "0")
  private int status;
  @Schema(description = "시도 횟수", required = true, example = "0")
  private int tryCount;
  @Schema(description = "처리자", required = true)
  private String operator;
}
