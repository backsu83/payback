package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.EventReward;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;

import java.util.Optional;

public interface EventRewardRepository {

  long save(EventRewardRequestDto request);

  Optional<EventReward> findEventReward(EventRewardRequestDto request);

  void saveStatus(final Long requestNo, final EventType eventType, final EventRequestStatusType statusType);
}
