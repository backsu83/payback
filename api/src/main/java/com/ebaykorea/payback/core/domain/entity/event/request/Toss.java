package com.ebaykorea.payback.core.domain.entity.event.request;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Toss extends EventReward {

  private static final int EXPIRATION_PERIOD = 30;
  private static final int ERS_NO = 8166;

  public static Toss of(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount) {
    return new Toss(requestNo, memberKey, saveAmount);
  }

  private Toss(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount
  ) {
    super(SaveIntegrationType.RealTime, requestNo, memberKey, saveAmount, EventType.Toss, EXPIRATION_PERIOD, null, ERS_NO, "", 0L, 0L);
  }

}
