package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.api.dto.event.TossEventRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.request.EventReward;
import com.ebaykorea.payback.core.domain.entity.event.request.TossEventReward;
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

  default TossEventReward map(TossEventRewardRequestDto source) {
    return TossEventReward.of(source.getRequestNo(), source.getMemberKey(), source.getSaveAmount());
  }
}
