package com.ebaykorea.payback.batch.domain;

import com.google.common.base.Strings;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Value
@Builder
public class SsgPointBatchUnit {
    private static final String dateTimeFormatForString = "yyMMddHHmmss";

    private static final String timeFormatForString = "MMddHH";

    private static final DateTimeFormatter DATE_TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString)
            .withZone(ZoneId.of("Asia/Seoul"));

    private static final DateTimeFormatter TIME_STRING_FORMATTER  = DateTimeFormatter.ofPattern(dateTimeFormatForString)
            .withZone(ZoneId.of("Asia/Seoul"));

    //"YYMMDDHH24MISS" + padding
    public String getVerifyTransactionNo() {
        String verifyTransactionNo = new StringBuilder()
                .append(DATE_TIME_STRING_FORMATTER.format(Instant.now()))
                .toString();
        return Strings.padEnd(verifyTransactionNo, 20, '0');
    }

    public String getRequestDate() {
        return DATE_TIME_STRING_FORMATTER.format(Instant.now().minus(1, ChronoUnit.DAYS));
    }
}
