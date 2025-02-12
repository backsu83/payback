package com.ebaykorea.payback.core.domain.entity.event.request;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType;
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType;
import com.ebaykorea.payback.core.domain.constant.SaveIntegrationType;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Review extends EventReward {

  private final ReviewReferenceType referenceType;

  private static final int EXPIRATION_PERIOD = 180;

  public Review(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount,
      final EventType eventType,
      final ReviewReferenceType referenceType,
      final ReviewPromotionType promotionType,
      final String defaultComments
  ) {
    super(SaveIntegrationType.Mass, requestNo, memberKey, saveAmount, eventType, EXPIRATION_PERIOD, referenceType.getCode(), promotionType.getGmarketCode(), defaultComments, 0L, requestNo);
    this.referenceType = referenceType;
    validate();
  }

  private void validate() {
    if (!isNormalReviewType() && !isPremiumReviewType()) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, "Review 또는 ReviewPremium type 만 가능합니다");
    }
  }
  private boolean isNormalReviewType() {
    return this.getEventType() == EventType.Review;
  }
  private boolean isPremiumReviewType() {
    return this.getEventType() == EventType.ReviewPremium;
  }

}
