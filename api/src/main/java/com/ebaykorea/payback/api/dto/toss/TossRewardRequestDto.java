package com.ebaykorea.payback.api.dto.toss;

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
public class TossRewardRequestDto {
  @Schema(description = "요청 아이디", required = true)
  @NotEmpty(message = "requestId cannot be empty")
  private String requestId;

  @Schema(description = "유저 정보", required = true)
  @NotEmpty(message = "userToken cannot be empty")
  private String userToken;

  @Schema(description = "적립 금액", required = true)
  @NotNull(message = "amount cannot be null")
  private BigDecimal amount;

  @Schema(description = "적립 사유")
  private String message;

  @Schema(description = "사후적립의 근거가 되는 사용자 거래내역 목록")
  private List<TossRewardRequestDetailDto> transactions;
}
