package com.ebaykorea.payback.consumer.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PaybackDateTimes {

  public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
  public static final String dateFormat = "yyyy-MM-dd";
  public static final String dateTimeFormatForString = "yyMMddHHmmss";
  public static final String dateFormatForString = "MMddHH";

  public static final DateTimeFormatter LOCAL_DATE_FORMATTER  = DateTimeFormatter.ofPattern(dateFormat)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter LOCAL_DATE_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateFormatForString)
      .withZone(ZoneId.of("Asia/Seoul"));

  public static final DateTimeFormatter LOCAL_DATE_TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString)
      .withZone(ZoneId.of("Asia/Seoul"));

}
