package com.ebaykorea.payback.core.domain.entity.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReviewReward extends SmileCashEvent {

  private static final int EXPIRATION_PERIOD = 180;
  public ReviewReward(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount,
      final EventType eventType,
      final ReviewReferenceType referenceType
  ) {
    super(requestNo, memberKey, saveAmount, eventType, EXPIRATION_PERIOD, referenceType.getCode(), 0, SaveIntegrationType.Mass);
  }
}
