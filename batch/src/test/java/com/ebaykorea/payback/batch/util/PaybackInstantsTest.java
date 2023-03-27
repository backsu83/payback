package com.ebaykorea.payback.batch.util;

import static com.ebaykorea.payback.batch.util.PaybackInstants.getDateTimeForString;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class PaybackInstantsTest {

  @Test
  void from() {
  }

  @Test
  void now() {
    var time = getDateTimeForString("HHmmss");
    var day = getDateTimeForString("MMdd");
    var dateTime = getDateTimeForString("yyyyMMdd");
    System.out.println(time);
    System.out.println(day);
    System.out.println(dateTime);
  }

  @Test
  void startOfDay() {
  }

  @Test
  void endOfDay() {
  }
}