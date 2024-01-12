package com.ebaykorea.payback.core.domain.entity.event;

import com.ebaykorea.payback.core.domain.constant.EventType;
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
public class ReviewReward extends SmileCashEvent {

  private final ReviewReferenceType referenceType;

  private static final int EXPIRATION_PERIOD = 180;
  private static final String NORMAL_TOUR_REWARD_COMMENT = "여행 후기 기본 적립";
  private static final String PREMIUM_TOUR_REWARD_COMMENT = "여행 후기 추가 적립";

  public ReviewReward(
      final long requestNo,
      final String memberKey,
      final BigDecimal saveAmount,
      final EventType eventType,
      final ReviewReferenceType referenceType
  ) {
    super(requestNo, memberKey, saveAmount, eventType, EXPIRATION_PERIOD, referenceType.getCode(), 0, SaveIntegrationType.Mass);
    this.referenceType = referenceType;

    validate();
  }

  private void validate() {
    if (!isNormalReviewType() && !isPremiumReviewType()) {
      throw new PaybackException(PaybackExceptionCode.DOMAIN_ENTITY_001, "Review 또는 ReviewPremium type 만 가능합니다");
    }
  }

  @Override
  public String getComments() {
    switch (referenceType) {
      case Tour:
        return isNormalReviewType() ? NORMAL_TOUR_REWARD_COMMENT : PREMIUM_TOUR_REWARD_COMMENT;
      default:
        return "";
    }
  }

  private boolean isNormalReviewType() {
    return this.getEventType() == EventType.Review;
  }
  private boolean isPremiumReviewType() {
    return this.getEventType() == EventType.ReviewPremium;
  }
}
