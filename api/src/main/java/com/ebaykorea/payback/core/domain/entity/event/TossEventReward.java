package com.ebaykorea.payback.core.domain.entity.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TossEventReward extends SmileCashEvent {

  private static final int EXPIRATION_PERIOD = 30;
  private static final int ERS_NO = 8166;

  public static TossEventReward of(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount) {
    return new TossEventReward(requestNo, memberKey, saveAmount);
  }

  private TossEventReward(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount
  ) {
    super(requestNo, memberKey, saveAmount, EventType.Toss, EXPIRATION_PERIOD, null, ERS_NO);
  }
}
