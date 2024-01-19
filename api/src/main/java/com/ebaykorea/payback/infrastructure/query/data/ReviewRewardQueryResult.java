package com.ebaykorea.payback.infrastructure.query.data;

import com.ebaykorea.payback.core.domain.constant.EventType;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRewardQueryResult {
  private EventType reviewType;
  private Boolean save;
  private BigDecimal saveAmount;
  private Instant saveDate;
}
