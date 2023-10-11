package com.ebaykorea.payback.api.dto.toss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossEventRewardResultRequestDto {
  @Schema(description = "요청 아이디", required = true)
  @NotEmpty(message = "requestId cannot be empty")
  private String requestId;

  @Schema(description = "유저 정보", required = true)
  @NotEmpty(message = "userToken cannot be empty")
  private String userToken;
}
