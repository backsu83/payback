package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;

public interface EventRewardRepository {

  long save(EventRewardRequestDto request);

  boolean alreadySaved(String requestId, EventType eventType);

  void saveStatus(final String requestId, final EventType eventType, final EventRequestStatusType statusType);
}
