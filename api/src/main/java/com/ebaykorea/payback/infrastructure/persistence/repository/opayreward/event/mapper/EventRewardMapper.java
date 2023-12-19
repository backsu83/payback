package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.entity.event.EventReward;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDto;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventRewardMapper {

  EventRewardRequestEntity map(Long requestNo, String tenantId, TossEventRewardRequestDto source);

  EventRewardRequestStatusEntity map(Long requestNo, EventRequestStatusType eventRequestStatus);

  EventReward map(EventRewardRequestEntity source);
}
