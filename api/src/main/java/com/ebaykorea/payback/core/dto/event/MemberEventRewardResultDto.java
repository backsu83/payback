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
public class MemberEventRewardResultDto {
  @Schema(description = "적립 요청 번호")
  private long requestNo;
  @Schema(description = "적립 요청 스마일페이 번호")
  private long smilePayNo;
  @Schema(description = "요청 결과", example = "0: 성공, -322: 중복요청")
  private int resultCode;
}