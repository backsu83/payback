package com.ebaykorea.payback.core.common.constant;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConstant {

    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormat = "yyyy-MM-dd";

    public static final DateTimeFormatter LOCAL_DATE_FORMATTER  = DateTimeFormatter.ofPattern(dateFormat)
            .withZone(ZoneId.of("Asia/Seoul"));

    public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormat)
            .withZone(ZoneId.of("Asia/Seoul"));
}
