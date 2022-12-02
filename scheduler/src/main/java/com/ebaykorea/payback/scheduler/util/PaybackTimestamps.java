package com.ebaykorea.payback.scheduler.util;

import java.sql.Timestamp;
import java.time.Instant;

public class PaybackTimestamps {
  public static Timestamp from(final Instant instant) {
    return instant == null ? null : Timestamp.from(instant);
  }
}
