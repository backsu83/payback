package com.ebaykorea.payback.infrastructure.query.auction;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.auction.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.infrastructure.query.ReviewRewardQuery;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.ReviewRewardQueryMapper;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionReviewRewardQuery implements ReviewRewardQuery {
  private final SmileCashSaveQueueRepository repository;
  private final ReviewRewardQueryMapper mapper;

  @Override
  public List<ReviewRewardQueryResult> getReviewReward(final String memberKey, final Long requestNo) {

    return repository.findByBizKey(String.valueOf(requestNo))
        .stream()
        .filter(findReviewReasonCode())
        .map(obj->mapper.map(obj))
        .collect(Collectors.toUnmodifiableList());
  }

  private Predicate<SmileCashSaveQueueEntity> findReviewReasonCode() {
    return entity -> entity.getReasonCode().equals(EventType.Review.getAuctionCode())
        || entity.getReasonCode().equals(EventType.ReviewPremium.getAuctionCode());
  }

}
