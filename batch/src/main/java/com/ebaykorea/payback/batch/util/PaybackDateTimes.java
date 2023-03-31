package com.ebaykorea.payback.batch.util;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PaybackDateTimes {

  public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
  public static final String dateFormat = "yyyy-MM-dd";
  public static final String dateTimeFormatForString = "yyyyMMddHHmmss";
  public static final String dateFormatForString = "MMddHH";

  public static final DateTimeFormatter DATE_FORMATTER  = DateTimeFormatter.ofPattern(dateFormat);
  public static final DateTimeFormatter DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat);
  public static final DateTimeFormatter DATE_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateFormatForString);
  public static final DateTimeFormatter DATE_TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString);
}
