package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import java.util.Optional;

public interface SmileCashEventRequestRepository {
  Optional<EventRewardResultDto> save(SmileCashEvent smileCashEvent);
  Optional<EventRewardResultDto> saveWithBudget(SmileCashEvent smileCashEvent);
  Optional<SmileCashEventResult> find(SmileCashEvent smileCashEvent);
}
