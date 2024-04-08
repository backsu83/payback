package com.ebaykorea.payback.infrastructure.query.gmkt;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.constant.ReviewPromotionType;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.entity.SmileCashEventRequestEntity;
import com.ebaykorea.payback.infrastructure.query.ReviewRewardQuery;
import com.ebaykorea.payback.infrastructure.query.data.ReviewRewardQueryResult;
import com.ebaykorea.payback.infrastructure.query.mapper.ReviewRewardQueryMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmktReviewRewardQuery implements ReviewRewardQuery {

  private final SmileCashEventEntityRepository repository;
  private final ReviewRewardQueryMapper mapper;

  @Override
  public List<ReviewRewardQueryResult> getReviewReward(final String memberKey , final Long requestNo) {
    return Stream.of(EventType.Review , EventType.ReviewPremium)
        .map(eventType -> {
          final var entity = repository.find(SmileCashEventRequestEntity.builder()
              .cashBalanceType(eventType.getGmarketCode())
              .custNo(memberKey)
              .refNo(requestNo)
              .ersNo(getPromotionId(eventType))
              .build());
          return entity.map(obj -> mapper.map(obj, eventType));
        })
        .flatMap(Optional::stream)
        .collect(Collectors.toUnmodifiableList());
  }

  public int getPromotionId(EventType eventType) {
    return eventType == EventType.Review ? ReviewPromotionType.Normal.getGmarketCode() : ReviewPromotionType.Premium.getGmarketCode();
  }

}
