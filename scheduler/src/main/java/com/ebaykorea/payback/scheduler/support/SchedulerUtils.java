package com.ebaykorea.payback.scheduler.support;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class SchedulerUtils {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
  private static final String dateFormat = "yyyyMMdd";

  public static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern(dateFormat)
      .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
      .toFormatter()
      .withZone(SEOUL);

  public static Instant now() {
    return Instant.now().atZone(SEOUL).toInstant();
  }
  public static String toStringWithDateFormat(final Instant instant) {
    return DATE_FORMATTER.format(instant);
  }

  public static String orElse(String nullable, String defaultValue) {
    return StringUtils.hasLength(nullable) ? nullable : defaultValue;
  }
}
