package com.ebaykorea.payback.core.repository;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;

public interface EventRewardApproveRepository {
  void approve(ApprovalEventReward approvalEventReward);
}
