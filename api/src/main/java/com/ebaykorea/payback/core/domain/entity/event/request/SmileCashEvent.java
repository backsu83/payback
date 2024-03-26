package com.ebaykorea.payback.core.domain.entity.event.request;

import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.util.PaybackInstants;
import com.ebaykorea.payback.util.PaybackNumbers;
import com.ebaykorea.payback.util.PaybackObjects;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public abstract class SmileCashEvent {
  private final SaveIntegrationType saveIntegrationType;
  private final long requestNo;
  private final String memberKey;
  private final BigDecimal saveAmount;
  private final EventType eventType;
  private final int expirationPeriod;
  private final Long eventNo;
  private final int ersNo;
  private final String comments;
  private final long budgetNo;
  private final Long orderNo;

  public Instant getExpirationDate() {
    return truncatedDays(PaybackInstants.now(), expirationPeriod);
  }

  public boolean isEventRewardEventType() {
    return eventType == EventType.DailyCheckIn ||
        eventType == EventType.OrderAssociated ||
        eventType == EventType.OrderDisassociated;
  }

  public boolean hasOrderNo() {
    return PaybackObjects.orElse(orderNo, 0L) > 0L;
  }
}
