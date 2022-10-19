package com.ebaykorea.payback.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class PaybackInstants {
  private static final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd")
      .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
      .toFormatter()
      .withZone(ZoneId.systemDefault());

  public static Instant from(final String dateString) {
    return dateFormatter.parse(dateString, Instant::from);
  }

}
