package com.ebaykorea.payback.infrastructure.persistence.repository.auction;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionSmileCashEventRepository implements SmileCashEventRepository {
  @Override
  public List<MemberCashbackResultDto> save(final String memberKey, final List<MemberCashbackRequestDto> requests) {
    //TODO
    return Collections.emptyList();
  }
}
