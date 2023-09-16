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

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static java.util.stream.Collectors.toUnmodifiableList;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventEntityMapper mapper;

  @Override
  public List<MemberCashbackResultDto> save(final String memberKey, final List<MemberCashbackRequestDto> requests) {
    return requests.stream()
        .map(request -> mapper.map(memberKey, request))
        .flatMap(entity -> repository.save(entity).stream()
            .map(resultEntity -> mapper.map(entity.getRefNo(), resultEntity)))
        .collect(toUnmodifiableList());
  }
}
