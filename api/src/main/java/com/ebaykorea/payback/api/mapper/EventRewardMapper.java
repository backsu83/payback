package com.ebaykorea.payback.api.mapper;

import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.api.dto.event.TossEventRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.request.EventPlatform;
import com.ebaykorea.payback.core.domain.entity.event.request.Toss;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardMapper {

  @Mapping(constant = "90", target = "expirationPeriod")
  EventPlatform map(EventRewardRequestDto source);

  default Toss map(TossEventRewardRequestDto source) {
    return Toss.of(source.getRequestNo(), source.getMemberKey(), source.getSaveAmount());
  }
}
