package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.EventReward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardMapper {

  @Mapping(constant = "90", target = "expirationPeriod")
  EventReward map(EventRewardRequestDto source);
}
