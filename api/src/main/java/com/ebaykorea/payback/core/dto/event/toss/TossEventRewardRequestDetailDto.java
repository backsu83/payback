package com.ebaykorea.payback.core.dto.event.toss;

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
  private String detailId;

  @Schema(description = "이벤트 금액 (ex: 거래금액)")
  private BigDecimal eventAmount;

  @Schema(description = "이벤트 발생 일자")
  private Instant eventDate;

  @Schema(description = "카드거래 승인번호")
  private String cardApprovalNo;

  @Schema(description = "마스킹 된 카드 번호")
  private String maskedCardNumber;

}
