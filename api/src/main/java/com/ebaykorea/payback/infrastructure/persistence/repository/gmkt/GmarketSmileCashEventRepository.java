package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashEventRepository repository;

  @Override
  public MemberCashbackResultDto save(final String memberKey, final List<MemberCashbackRequestDto> requests) {

    return null;
  }
}
