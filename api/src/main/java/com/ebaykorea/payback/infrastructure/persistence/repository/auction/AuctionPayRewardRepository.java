package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import com.ebaykorea.payback.core.domain.entity.cashback.PayCashback;
import com.ebaykorea.payback.core.domain.entity.order.KeyMap;
import com.ebaykorea.payback.core.repository.PayRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionPayRewardRepository implements PayRewardRepository {
  @Override
  public void save(PayCashback payCashback) {

  }

  @Override
  public boolean hasAlreadySaved(KeyMap keyMap) {
    return true;
  }
}
