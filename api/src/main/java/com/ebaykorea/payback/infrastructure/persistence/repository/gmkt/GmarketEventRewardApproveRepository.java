package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.EventRewardApproveRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmileCashEventApprovalEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketEventRewardApproveRepository implements EventRewardApproveRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventApprovalEntityMapper mapper;

  @Override
  @Transactional
  public void approve(final ApprovalEventReward approvalEventReward) {
    repository.findBySmilePayNo(approvalEventReward.getSavingNo())
        .filter(SmileCashEventEntity::canBeApproved)
        .orElseThrow(() -> new PaybackException(PERSIST_002, approvalEventReward.toString()));

    repository.update(mapper.map(approvalEventReward));
  }
}
