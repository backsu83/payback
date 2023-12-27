package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.ReviewRewardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardReviewMapper {

  @Mapping(source = "eventType", target = "eventType")
  @Mapping(source = "request.requestType.code", target = "eventNo")
  EventRewardRequestDto map(ReviewRewardRequestDto request, EventType eventType);

}
