package com.ebaykorea.payback.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PaybackDateTimes {

  public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
  public static final String dateFormat = "yyyy-MM-dd";
  public static final String dateTimeFormatForString = "yyMMddHHmmss";
  public static final String timeFormatForString = "MMddHH";

  public static final DateTimeFormatter DATE_FORMATTER  = DateTimeFormatter.ofPattern(dateFormat)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter DATE_TIME_UTC_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat)
      .withZone(ZoneId.of("UTC"));

  public static final DateTimeFormatter TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(timeFormatForString)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter DATE_TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static DateTimeFormatter getDateFormatterBy(String format) {
    return DateTimeFormatter.ofPattern(format)
        .withZone(ZoneId.of("Asia/Seoul"));
  }

}
