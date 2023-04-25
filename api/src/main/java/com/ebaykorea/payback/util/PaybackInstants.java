package com.ebaykorea.payback.util;

import java.io.Console;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class PaybackInstants {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

  public static Instant getDefaultEnableDate(final Instant orderDate) {
    return orderDate.atZone(SEOUL)
        .truncatedTo(ChronoUnit.DAYS).plus(30, ChronoUnit.DAYS)
        .toInstant();
  }

  public static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd")
      .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
      .toFormatter()
      .withZone(SEOUL);

  public static Instant from(final Timestamp timestamp) {
    return timestamp == null ? null : timestamp.toInstant();
  }

  public static Instant now() {
    return Instant.now().atZone(SEOUL).toInstant();
  }

  public static String toStringBy(final Instant datetime) {
    return LocalDateTime.ofInstant(datetime ,SEOUL).format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
  }
}
