package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType;
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.request.TossRewardRequestResult;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    imports = EventType.class
)
public interface TossRewardRequestMapper {

  @Mapping(expression = "java(EventType.Toss)", target = "eventType")
  @Mapping(source = "source.amount", target = "saveAmount")
  EventRewardRequestEntity map(Long requestNo, String tenantId, TossRewardRequestDto source);

  EventRewardRequestStatusEntity map(Long requestNo, TossRewardRequestStatusType eventRequestStatus);

  TossRewardRequestResult map(EventRewardRequestEntity source);
}
