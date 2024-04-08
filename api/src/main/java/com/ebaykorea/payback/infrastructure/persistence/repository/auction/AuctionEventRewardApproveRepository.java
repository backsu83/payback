package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.EventRewardApproveRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveApprovalRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.mapper.SmileCashSaveApprovalEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionEventRewardApproveRepository implements EventRewardApproveRepository {

  private final SmileCashSaveApprovalRepository approvalRepository;
  private final SmileCashSaveQueueRepository saveQueueRepository;
  private final SmileCashSaveApprovalEntityMapper mapper;

  private static final int SAVED = 1;
  @Override
  @Transactional
  public void approve(final ApprovalEventReward approvalEventReward) {
    final var saveQueueEntity = saveQueueRepository.findByIacTxid(approvalEventReward.getSavingNo())
        .filter(SmileCashSaveQueueEntity::canBeApproved)
        .orElseThrow(() -> new PaybackException(PERSIST_002, approvalEventReward.toString()));

    final var approvalEntity = mapper.map(approvalEventReward, saveQueueEntity);
    approvalRepository.save(approvalEntity);
    saveQueueRepository.update(approvalEventReward.getSavingNo(), SAVED, approvalEntity.getInsertOperator());
  }
}
