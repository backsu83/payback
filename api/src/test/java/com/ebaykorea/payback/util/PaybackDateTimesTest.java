package com.ebaykorea.payback.util;

import static com.ebaykorea.payback.util.PaybackInstants.DATE_FORMATTER;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class PaybackDateTimesTest {

  @Test
  void dateTimeTest() {
    var date = DATE_FORMATTER.parse("2023-03-29" , Instant::from);
    System.out.println(date);
  }
}