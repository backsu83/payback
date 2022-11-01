package com.ebaykorea.payback.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class PaybackInstants {
  private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

  public static Instant getDefaultEnableDate(final Instant orderDate) {
    return orderDate.atZone(SEOUL)
        .truncatedTo(ChronoUnit.DAYS).plus(30, ChronoUnit.DAYS)
        .toInstant();
  }

}
