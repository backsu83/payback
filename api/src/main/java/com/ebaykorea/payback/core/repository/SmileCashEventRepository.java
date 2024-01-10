package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEventResult;
import com.ebaykorea.payback.api.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;

import java.util.Optional;

public interface SmileCashEventRepository {
  Optional<EventRewardResultDto> save(SmileCashEvent smileCashEvent);
  Optional<EventRewardResultDto> saveWithBudget(SmileCashEvent smileCashEvent);
  Optional<SmileCashEventResult> find(SmileCashEvent smileCashEvent);
}
