package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType;
import com.ebaykorea.payback.core.domain.entity.event.ReviewReward;
import com.ebaykorea.payback.api.dto.review.ReviewRewardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewRewardMapper {

  ReviewReward map(ReviewRewardRequestDto request, EventType eventType, ReviewPromotionType promotionType);

}
