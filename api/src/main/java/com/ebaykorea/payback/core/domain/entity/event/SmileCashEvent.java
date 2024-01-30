package com.ebaykorea.payback.core.domain.entity.event;

import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.util.PaybackInstants;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class SmileCashEvent {
  private final long requestNo;
  private final String memberKey;
  private final BigDecimal saveAmount;
  private final EventType eventType;
  private final int expirationPeriod;
  private final Long eventNo;
  private final int ersNo;
  private final SaveIntegrationType saveIntegrationType;

  public long getBudgetNo() {
    return 0;
  }

  public Instant getExpirationDate() {
    return truncatedDays(PaybackInstants.now(), expirationPeriod);
  }
  public String getComments() {
    return "";
  }

  public int getRequestStatus() {
    return 0;
  }

  public int getErsNo() {
    return 0;
  }
}
