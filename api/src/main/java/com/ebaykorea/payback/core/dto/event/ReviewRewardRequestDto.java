package com.ebaykorea.payback.core.dto.event;

import com.ebaykorea.payback.core.domain.constant.ReferenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRewardRequestDto {
  @Schema(description = "요청 아이디", required = true)
  @NotEmpty(message = "requestId cannot be empty")
  private String requestId;

  @Schema(description = "적립 요청번호 (주문번호)", required = true)
  @NotNull(message = "requestNo cannot be null")
  private Long requestNo;

  @Schema(description = "적립 금액", required = true)
  @Positive
  @NotNull(message = "saveAmount cannot be null")
  private BigDecimal saveAmount;

  @Schema(description = "출처 타입 (코어, 여행)", required = true)
  @NotNull(message = "referenceType cannot be empty")
  private ReferenceType caller;

}
