package com.ebaykorea.payback.infrastructure.query.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class SsgPointTargetQueryData {
  @Schema(description = "적립 예상 일")
  Instant expectSaveDate;
  @Schema(description = "적립 금액")
  BigDecimal saveAmount;
  @Schema(description = "적립 여부")
  boolean saved;
}
