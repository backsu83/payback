package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.entity.event.EventReward;
import com.ebaykorea.payback.core.dto.event.toss.TossEventRewardRequestDto;

import java.util.Optional;

public interface EventRewardRepository {

  long save(TossEventRewardRequestDto request);

  Optional<EventReward> findEventReward(TossEventRewardRequestDto request);

  void saveStatus(final Long requestNo, final EventRequestStatusType statusType);
}
