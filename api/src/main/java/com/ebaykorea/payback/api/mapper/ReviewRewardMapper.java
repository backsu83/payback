package com.ebaykorea.payback.api.mapper;

import static com.ebaykorea.payback.core.domain.constant.ReviewRewardConstants.NORMAL_TOUR_REWARD_COMMENT;
import static com.ebaykorea.payback.core.domain.constant.ReviewRewardConstants.PREMIUM_TOUR_REWARD_COMMENT;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType;
import com.ebaykorea.payback.core.domain.constant.ReviewReferenceType;
import com.ebaykorea.payback.core.domain.entity.event.request.Review;
import com.ebaykorea.payback.api.dto.review.ReviewRewardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewRewardMapper {

  @Mapping(expression = "java(mapToDefaultComments(request.getReferenceType(), eventType))", target = "defaultComments")
  Review map(ReviewRewardRequestDto request, EventType eventType, ReviewPromotionType promotionType);

  default String mapToDefaultComments(final ReviewReferenceType referenceType, final EventType eventType) {
    if (referenceType == ReviewReferenceType.Tour) {
      return eventType == EventType.Review ? NORMAL_TOUR_REWARD_COMMENT : PREMIUM_TOUR_REWARD_COMMENT;
    }
    return "";
  }
}
