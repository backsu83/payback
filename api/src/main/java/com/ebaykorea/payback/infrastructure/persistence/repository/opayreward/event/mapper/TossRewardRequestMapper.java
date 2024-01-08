package com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper;

import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType;
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import com.ebaykorea.payback.core.domain.entity.event.TossRewardRequestResult;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.entity.EventRewardRequestStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TossRewardRequestMapper {

  EventRewardRequestEntity map(Long requestNo, String tenantId, TossRewardRequestDto source);

  EventRewardRequestStatusEntity map(Long requestNo, TossRewardRequestStatusType eventRequestStatus);

  TossRewardRequestResult map(EventRewardRequestEntity source);
}
