package com.ebaykorea.payback.infrastructure.query.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static java.util.stream.Collectors.toUnmodifiableList;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveApprovalRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.query.ReviewRewardQuery;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.ReviewRewardQueryMapper;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionReviewRewardQuery implements ReviewRewardQuery {
  private final SmileCashSaveQueueRepository smileCashSaveQueueRepository;
  private final SmileCashSaveApprovalRepository smileCashSaveApprovalRepository;
  private final ReviewRewardQueryMapper mapper;

  @Override
  public List<ReviewRewardQueryResult> getReviewReward(final String memberKey, final Long requestNo) {
    return smileCashSaveQueueRepository.findByBizKey(String.valueOf(requestNo)).stream()
        .filter(isReviewReasonCode())
        .map(entity -> smileCashSaveApprovalRepository.findById(entity.getTxId()))
        .flatMap(Optional::stream)
        .map(mapper::mapFromSmileCashSaveApprovalEntity)
        .collect(toUnmodifiableList());
  }

  private Predicate<SmileCashSaveQueueEntity> isReviewReasonCode() {
    return entity -> entity.getReasonCode().equals(EventType.Review.getAuctionCode())
        || entity.getReasonCode().equals(EventType.ReviewPremium.getAuctionCode());
  }

}
