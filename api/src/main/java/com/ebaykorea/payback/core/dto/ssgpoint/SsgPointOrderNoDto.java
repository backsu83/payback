package com.ebaykorea.payback.core.dto.ssgpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointOrderNoDto  {
  private Long orderNo;
  private String siteType;
  private Instant insertDate;
  private Instant updateDate;

  public static SsgPointOrderNoDto of(final Long orderNo,
                                      final String siteType,
                                      final Instant insertDate,
                                      final Instant updateDate
  ) {
    return new SsgPointOrderNoDto(orderNo,
            siteType,
            insertDate,
            updateDate);
  }
}
