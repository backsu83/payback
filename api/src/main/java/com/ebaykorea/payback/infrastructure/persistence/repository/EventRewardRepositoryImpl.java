package com.ebaykorea.payback.infrastructure.persistence.repository;


import com.ebaykorea.payback.config.properties.SaturnApplicationProperties;
import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.entity.event.EventReward;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.EventRewardRequestRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.EventRewardRequestStatusRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper.EventRewardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventRewardRepositoryImpl implements EventRewardRepository {
  private final EventRewardRequestRepository repository;
  private final EventRewardRequestStatusRepository statusRepository;

  private final EventRewardMapper mapper;
  private final SaturnApplicationProperties tenantProperties;

  @Transactional
  @Override
  public long save(final EventRewardRequestDto request) {
    final var requestNo = repository.getNextRequestNo();

    save(requestNo, request);
    saveStatus(requestNo, EventRequestStatusType.Created);

    return requestNo;
  }

  @Override
  public Optional<EventReward> findEventReward(final EventRewardRequestDto request) {
    return repository.findByRequestIdAndUserTokenAndEventType(request.getRequestId(), request.getUserToken(), request.getEventType())
        .map(mapper::map);
  }

  private void save(final long requestNo, final EventRewardRequestDto request) {
    final var entity = mapper.map(requestNo, tenantProperties.getTenantId(), request);
    repository.save(entity);
  }

  @Override
  public void saveStatus(final Long requestNo, final EventRequestStatusType statusType) {
    final var statusEntity = mapper.map(requestNo, statusType);
    statusRepository.save(statusEntity);
  }
}
