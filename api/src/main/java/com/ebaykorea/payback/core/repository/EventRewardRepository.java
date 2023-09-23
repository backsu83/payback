package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;

public interface EventRewardRepository {

  long save(EventRewardRequestDto request);
}
