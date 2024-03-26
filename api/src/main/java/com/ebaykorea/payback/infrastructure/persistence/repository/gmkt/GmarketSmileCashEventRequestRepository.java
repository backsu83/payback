package com.ebaykorea.payback.infrastructure.persistence.repository.gmkt;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.PERSIST_002;

import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEvent;
import com.ebaykorea.payback.core.domain.entity.event.request.SmileCashEventResult;
import com.ebaykorea.payback.core.dto.event.EventRewardResultDto;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.repository.SmileCashEventRequestRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.mapper.SmileCashEventEntityMapper;
import com.ebaykorea.payback.infrastructure.persistence.repository.gmkt.stardb.SmileCashEventEntityRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile(GMARKET_TENANT)
@Service
@RequiredArgsConstructor
public class GmarketSmileCashEventRequestRepository implements SmileCashEventRequestRepository {

  private final SmileCashEventEntityRepository repository;
  private final SmileCashEventEntityMapper mapper;

  @Transactional
  @Override
  public Optional<EventRewardResultDto> save(final SmileCashEvent smileCashEvent) {
    if (smileCashEvent.isEventRewardEventType()) {
      // TODO: 지마켓 이벤트 리워드 적립은 테스트 후 예외 처리 제거
      throw new PaybackException(PERSIST_002, smileCashEvent.getEventType().getName());
    }

    final var entity = mapper.map(smileCashEvent);
    return repository.save(entity)
        .map(resultEntity -> mapper.map(entity.getRefNo(), resultEntity));
  }

  @Override
  public Optional<EventRewardResultDto> saveWithBudget(final SmileCashEvent smileCashEvent) {
    return save(smileCashEvent);
  }

  @Override
  public Optional<SmileCashEventResult> find(final SmileCashEvent smileCashEvent) {
    final var entity = mapper.map(smileCashEvent);
    return repository.find(entity)
        .map(mapper::map);
  }
}
