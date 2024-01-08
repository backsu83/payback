package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.SmileCashEvent;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.dto.event.SetEventRewardRequestDto;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.SmileCashEventRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmileCashEventEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRepository implements SmileCashEventRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventEntityMapper mapper;

  @Transactional
  @Override
  public Optional<EventRewardResultDto> save(final EventRewardRequestDto request) {
    if (request.getEventType() == EventType.DailyCheckIn) {
      throw new PaybackException(PERSIST_002, request.getEventType().getName());
    }

    final var entity = mapper.map(request);
    return repository.save(entity)
        .map(resultEntity -> mapper.map(entity.getRefNo(), resultEntity));
  }

  @Override
  public Optional<EventRewardResultDto> saveWithBudget(EventRewardRequestDto request) {
    return save(request);
  }

  @Override
  public void set(final Long smilePayNo, final SetEventRewardRequestDto request) {
    final var entity = mapper.map(smilePayNo, request);
    repository.update(entity);
  }

  @Override
  public Optional<SmileCashEvent> find(final EventRewardRequestDto request) {
    final var entity = mapper.map(request);
    return repository.find(entity)
        .map(mapper::map);
  }
}
