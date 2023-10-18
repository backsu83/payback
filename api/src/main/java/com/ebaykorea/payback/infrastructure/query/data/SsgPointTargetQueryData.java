package com.ebaykorea.payback.infrastructure.query.data;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static com.ebaykorea.payback.util.PaybackCollections.orEmptyStream;
import static com.ebaykorea.payback.util.PaybackDecimals.summarizing;

@Getter
public class SsgPointTargetQueryData {
  private final BigDecimal totalAmount;
  private final Instant expectSaveDate;

  public static final SsgPointTargetQueryData EMPTY = SsgPointTargetQueryData.of(Collections.emptyList());

  public static SsgPointTargetQueryData of(final List<SsgPointTargetUnitQueryData> targetedSsgPointUnits) {
    final var totalAmount = orEmptyStream(targetedSsgPointUnits)
        .map(SsgPointTargetUnitQueryData::getAmount)
        .collect(summarizing());
    final var expectSaveDate = orEmptyStream(targetedSsgPointUnits)
        .map(SsgPointTargetUnitQueryData::getExpectSaveDate)
        .findAny()
        .orElse(null);

    return new SsgPointTargetQueryData(totalAmount, expectSaveDate);
  }

  private SsgPointTargetQueryData(final BigDecimal totalAmount, final Instant expectSaveDate) {
    this.totalAmount = totalAmount;
    this.expectSaveDate = expectSaveDate;
  }
}
