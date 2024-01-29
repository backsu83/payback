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

  private static final int MASS_REQUEST_READY = 3;

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
    super(requestNo, memberKey, saveAmount, eventType, expirationPeriod, eventNo, 0, SaveIntegrationType.RealTime);

    this.budgetNo = budgetNo;
    this.expirationDate = expirationDate;
    this.comment = comment;

    validate();
  }

  private void validate() {
    if (this.getEventType() != EventType.DailyCheckIn) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, "DailyCheckIn type 만 가능합니다");
    }
  }

  @Override
  public String getComments() {
    return comment;
  }

  @Override
  public int getRequestStatus() {
    return MASS_REQUEST_READY;
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
