package com.ebaykorea.payback.util;

import java.time.Instant;
import org.junit.jupiter.api.Test;

import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_FORMATTER;

class PaybackDateTimesTest {

  @Test
  void dateTimeTest() {
    var date = DATE_FORMATTER.parse("2023-03-29" , Instant::from);
    System.out.println(date);
  }
}