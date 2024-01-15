package com.ebaykorea.payback.infrastructure.query.data;


import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRewardQueryResult {

  private String reviewType;
  private String isSave;
  private BigDecimal amount;
  private Instant saveDate;

}
