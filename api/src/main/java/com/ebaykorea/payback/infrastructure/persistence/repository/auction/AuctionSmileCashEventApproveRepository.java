package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.core.domain.entity.event.approval.ApprovalEventReward;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventApproveRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionSmileCashEventApproveRepository implements SmileCashEventApproveRepository {

  @Override
  public Optional<EventRewardResultDto> approve(final ApprovalEventReward approvalEventReward) {
//    smileCashSaveApprovalRepository.saveApproval(smileCashSaveMapper.map(approveDto, smileUserKey, entity));
//    smileCashSaveQueueRepository.update(entity.getSeqNo(), MASS_SAVED, entity.getRetryCount(), entity.getInsertOperator());
    return Optional.empty();
  }
}
