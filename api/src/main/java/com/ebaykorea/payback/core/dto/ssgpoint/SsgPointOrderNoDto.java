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
  private String insertOperator;
  private Instant updateDate;
  private String updateOperator;

  public static SsgPointOrderNoDto of(final Long orderNo,
                                      final String siteType,
                                      final Instant insertDate,
                                      final String insertOperator,
                                      final Instant updateDate,
                                      final String updateOperator
  ) {
    return new SsgPointOrderNoDto(orderNo,
            siteType,
            insertDate,
            insertOperator,
            updateDate,
            updateOperator);
  }
}
