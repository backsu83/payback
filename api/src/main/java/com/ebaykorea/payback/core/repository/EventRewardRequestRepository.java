package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.request.EventReward;
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import java.util.Optional;

public interface EventRewardRequestRepository {
  Optional<EventRewardResultDto> save(EventReward smileCashEvent);
  Optional<EventRewardResultDto> saveWithBudget(EventReward smileCashEvent);
  Optional<SmileCashEventResult> find(EventReward smileCashEvent);
}
