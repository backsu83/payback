package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.MemberEventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmileCashEventEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventEntityMapper mapper;

  @Transactional
  @Override
  public Optional<MemberEventRewardResultDto> save(final String buyerNo, final MemberEventRewardRequestDto request) {
    final var entity = mapper.map(buyerNo, request);
    return repository.save(entity)
        .map(resultEntity -> mapper.map(entity.getRefNo(), resultEntity));
  }

  @Override
  public void set(final Long smilePayNo, final SetEventRewardRequestDto request) {
    final var entity = mapper.map(smilePayNo, request);
    repository.update(entity);
  }

  @Override
  public Optional<SmileCashEvent> find(final String buyerNo, final MemberEventRewardRequestDto request) {
    final var entity = mapper.map(buyerNo, request);
    return repository.find(entity)
        .map(mapper::map);
  }
}