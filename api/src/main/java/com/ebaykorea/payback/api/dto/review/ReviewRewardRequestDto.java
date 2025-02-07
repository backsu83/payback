package com.ebaykorea.payback.api.dto.review;

import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType;
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
  @NotEmpty(message = "memberKey cannot be empty")
  private String memberKey;

  @Schema(description = "적립 요청번호 (주문번호)", required = true)
  @NotNull(message = "requestNo cannot be null")
  private Long requestNo;

  @Schema(description = "적립 금액", required = true)
  @Positive(message = "saveAmount must be greater than 0")
  @NotNull(message = "saveAmount cannot be null")
  private BigDecimal saveAmount;

  @Schema(description = "출처 타입 (Core, Tour)", example = "Core,Tour", required = true)
  @NotNull(message = "caller cannot be empty")
  private ReviewReferenceType referenceType;
}
