package com.ebaykorea.payback.core.domain.entity.event;

import static com.ebaykorea.payback.util.PaybackInstants.truncatedDays;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import com.ebaykorea.payback.util.PaybackInstants;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventReward extends SmileCashEvent {

  private final Long budgetNo;
  private final Instant expirationDate;
  private final String comment;

  public EventReward(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount,
      final EventType eventType,
      final int expirationPeriod,
      final Long eventNo,
      final Long budgetNo,
      final Instant expirationDate,
      final String comment
  ) {
    super(requestNo, memberKey, saveAmount, eventType, expirationPeriod, eventNo, 0, SaveIntegrationType.Mass);

    this.budgetNo = budgetNo;
    this.expirationDate = expirationDate;
    this.comment = comment;

    validate();
  }

  private void validate() {
    if (this.getEventType() != EventType.DailyCheckIn &&
        this.getEventType() != EventType.OrderAssociated &&
        this.getEventType() != EventType.OrderDisassociated) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, String.format("올바르지 않은 이벤트 타입입니다. %s", this.getEventType()));
    }
  }

  @Override
  public String getComments() {
    return comment;
  }

  @Override
  public long getBudgetNo() {
    return budgetNo;
  }

  @Override
  public Instant getExpirationDate() {
    return Optional.ofNullable(expirationDate)
        .orElse(truncatedDays(PaybackInstants.now(), this.getExpirationPeriod()));
  }

}
