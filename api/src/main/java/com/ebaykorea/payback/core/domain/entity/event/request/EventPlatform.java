package com.ebaykorea.payback.core.domain.entity.event.request;

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
public class EventPlatform extends EventReward {

  private final Instant expirationDate;

  public EventPlatform(
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
    super(SaveIntegrationType.Mass, requestNo, memberKey, saveAmount, eventType, expirationPeriod, eventNo, 0, comment, budgetNo, 0L);

    this.expirationDate = expirationDate;

    validate();
  }

  private void validate() {
    if (this.getEventType() != EventType.DailyCheckIn &&
        this.getEventType() != EventType.OrderAssociated &&
        this.getEventType() != EventType.OrderDisassociated) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, String.format("올바르지 않은 이벤트 타입입니다. %s", this.getEventType()));
    }

    if (hasExpirationDatePassed()) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, "이미 지난 유효기간 입니다.");
    }

    if (this.getBudgetNo() <= 0) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, "예산 정보가 없습니다.");
    }
  }

  private boolean hasExpirationDatePassed() {
    return Optional.ofNullable(expirationDate)
        .map(date -> PaybackInstants.now().isAfter(date))
        .orElse(false);
  }

  @Override
  public Instant getExpirationDate() {
    return Optional.ofNullable(expirationDate)
        .orElse(truncatedDays(PaybackInstants.now(), this.getExpirationPeriod()));
  }

}
