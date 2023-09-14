package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import com.ebaykorea.payback.core.dto.member.MemberCashbackRequestDto;
import com.ebaykorea.payback.core.dto.member.MemberCashbackResultDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmileCashEventEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventEntityMapper entityMapper;

  @Override
  public MemberCashbackResultDto save(final String memberKey, final List<MemberCashbackRequestDto> requests) {

    final var result = entityMapper.map(memberKey, requests).stream()
        .flatMap(entity -> repository.save(entity).stream())
        .collect(Collectors.toUnmodifiableList());
    return null;
  }
}
