package com.ebaykorea.payback.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class PaybackInstants {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

  public static Instant getDefaultEnableDate(final Instant orderDate) {
    return orderDate.atZone(SEOUL)
        .truncatedTo(ChronoUnit.DAYS).plus(30, ChronoUnit.DAYS)
        .toInstant();
  }
  public static Instant from(final Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toInstant();
  }

  public static Instant now() {
    return Instant.now().atZone(SEOUL).toInstant();
  }
}
