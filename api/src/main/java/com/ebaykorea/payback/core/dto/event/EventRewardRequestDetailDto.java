package com.ebaykorea.payback.core.dto.event;

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
public class EventRewardRequestDetailDto {
  @Schema(description = "이벤트 상세 아이디", required = true)
  private String detailId; //approvalNo

  @Schema(description = "이벤트 금액")
  private BigDecimal eventAmount; //payAmount

  @Schema(description = "암호화 된 카드 번호")
  private String encryptedCardNo;

  @Schema(description = "암호화 된 사업자등록번호")
  private String encryptedCorporateRegNo;

  @Schema(description = "이벤트 발생 일자")
  private Instant eventDate; //transactAt
}
