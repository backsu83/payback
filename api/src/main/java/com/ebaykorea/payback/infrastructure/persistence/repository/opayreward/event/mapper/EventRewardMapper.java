package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDetailDto;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestDetailEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardMapper {

  EventRewardRequestEntity map(Long requestNo, String tenantId, EventRewardRequestDto source);

  EventRewardRequestDetailEntity map(Long seq, Long requestNo, EventRewardRequestDetailDto source);

  EventRewardRequestStatusEntity map(Long requestNo, EventType eventType, EventRequestStatusType eventRequestStatus);
}
