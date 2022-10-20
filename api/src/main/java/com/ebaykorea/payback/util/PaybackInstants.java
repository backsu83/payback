package com.ebaykorea.payback.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

public class PaybackInstants {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

  public static Instant from(final String dateString) {
    try {
      return LocalDateTime.parse(dateString).atZone(SEOUL).toInstant();
    } catch (DateTimeParseException e) {
      return LocalDate.parse(dateString).atStartOfDay().atZone(SEOUL).toInstant();
    }
  }

}
