package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import java.util.Optional;

public interface SmileCashEventApproveRepository {
  Optional<EventRewardResultDto> approve(ApprovalEventReward approvalEventReward);
}
