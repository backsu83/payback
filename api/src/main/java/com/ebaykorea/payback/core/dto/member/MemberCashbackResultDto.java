package com.ebaykorea.payback.core.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MemberCashbackResultDto {
  private long payNo;
  @Schema(description = "적립 요청 키")
  private String saveRequestKey;
  @Schema(description = "적립 예정 일")
  private Instant expectSaveDate;
  @Schema(description = "요청 결과", example = "'SUCCESS' OR 'ALREADY_PROCESSED'")
  private String resultMessage;
}
