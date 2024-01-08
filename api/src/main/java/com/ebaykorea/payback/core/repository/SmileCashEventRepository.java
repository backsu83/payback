package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;

import java.util.Optional;

public interface SmileCashEventRepository {
  Optional<EventRewardResultDto> save(EventRewardRequestDto request);
  Optional<EventRewardResultDto> saveWithBudget(EventRewardRequestDto request);
  void set(Long smilePayNo, SetEventRewardRequestDto request);

  Optional<SmileCashEvent> find(EventRewardRequestDto request);
}
