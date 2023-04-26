package com.ebaykorea.payback.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PaybackDateTimes {

  public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

  private static final String dateFormat = "yyyy-MM-dd";
  private static final String dateTimeFormatForString = "yyMMddHHmmss";
  private static final String timeFormatForString = "MMddHH";
  private static final ZoneId DEFAULT_TIME_ZONE = ZoneId.of("Asia/Seoul");

  public static final DateTimeFormatter DATE_FORMATTER  = DateTimeFormatter.ofPattern(dateFormat)
      .withZone(DEFAULT_TIME_ZONE);

  public static final DateTimeFormatter DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat)
      .withZone(DEFAULT_TIME_ZONE);

  public static final DateTimeFormatter TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(timeFormatForString)
      .withZone(DEFAULT_TIME_ZONE);

  public static final DateTimeFormatter DATE_TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString)
      .withZone(DEFAULT_TIME_ZONE);
}
