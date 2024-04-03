package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType;
import com.ebaykorea.payback.core.domain.entity.event.request.TossRewardRequestResult;

import java.util.Optional;

public interface TossEventRewardRequestRepository {

  long save(TossRewardRequestDto request);

  Optional<TossRewardRequestResult> find(TossRewardRequestDto request);

  void saveStatus(final Long requestNo, final TossRewardRequestStatusType statusType);
}
