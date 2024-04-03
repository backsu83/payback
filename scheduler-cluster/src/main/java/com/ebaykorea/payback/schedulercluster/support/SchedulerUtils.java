package com.ebaykorea.payback.schedulercluster.support;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import org.springframework.util.StringUtils;

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

  public static String orEmpty(final String nullable) {
    return orElse(nullable, "");
  }

  public static boolean isBlank(String str) {
    return str == null || str.isBlank();
  }
  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  public static boolean isGreaterThanZero(final BigDecimal decimal) {
    return orZero(decimal).compareTo(ZERO) > 0;
  }

  public static BigDecimal orZero(final BigDecimal decimal) {
    return decimal == null ? ZERO : decimal;
  }
}
