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

  EventRewardRequestEntity map(Long eventRequestNo, EventRewardRequestDto source);


  EventRewardRequestDetailEntity map(Long seq, Long eventRequestNo, EventRewardRequestDetailDto source);

  EventRewardRequestStatusEntity map(String requestId, EventType eventType, EventRequestStatusType eventRequestStatus);
}
