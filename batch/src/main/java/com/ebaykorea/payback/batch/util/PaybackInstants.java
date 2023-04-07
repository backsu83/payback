package com.ebaykorea.payback.batch.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

  public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
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

  public static Instant startOfDay() {
    LocalDateTime startOfDay = LocalDateTime.ofInstant(Instant.now() , SEOUL).with(LocalTime.MIN);
    Instant startInstant = startOfDay.atZone(SEOUL).toInstant();
    return startInstant;
  }

  public static Instant endOfDay() {
    LocalDateTime endOfDay = LocalDateTime.ofInstant(Instant.now() , SEOUL).with(LocalTime.MAX);
    Instant endInstant = endOfDay.atZone(SEOUL).toInstant();
    return endInstant;
  }

  public static String getDateTimeFormatBy(final String dateFormat) {
    return DateTimeFormatter.ofPattern(dateFormat)
        .withZone(ZoneId.of("Asia/Seoul")).format(now());
  }
}
