package com.ebaykorea.payback.api.dto.toss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TossEventRewardRequestDetailDto {
  @Schema(description = "이벤트 상세 아이디", required = true)
  private String id;

  @Schema(description = "거래 금액")
  private BigDecimal amount;

  @Schema(description = "이벤트 발생 일자")
  private Instant transactAt;

  @Schema(description = "카드거래 승인번호")
  private String cardApprovalNo;

  @Schema(description = "마스킹 된 카드 번호")
  private String maskedCardNumber;
}
