package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.repository.EventRewardApproveRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketEventRewardApproveRepository implements EventRewardApproveRepository {

  private final SmileCashEventEntityRepository repository;
  @Override
  public void approve(final ApprovalEventReward approvalEventReward) {
    //TODO
  }
}
