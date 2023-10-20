package com.ebaykorea.payback.infrastructure.query.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

import static java.math.BigDecimal.ZERO;

@Value
@Builder
@AllArgsConstructor(staticName = "of")
public class SmileCardQueryData {
  @Schema(description = "스마일카드 타입", example = "T0, T1, T2, T3")
  String type;

  @Schema(description = "스마일카드 적립 금액")
  BigDecimal saveAmount;
  @Schema(description = "스마일카드 적립 예정 일수", example = "10")
  Integer expectSaveDays;

  @Schema(description = "스마일카드 추가 적립 금액")
  BigDecimal additionalSaveAmount;
  @Schema(description = "스마일카드 추가 적립 예정일")
  Instant additionalExpectSaveDate;

  public static final SmileCardQueryData EMPTY = SmileCardQueryData.of("", ZERO, null, ZERO, null);
}
