package com.ebaykorea.payback.infrastructure.persistence.repository;


import com.ebaykorea.payback.core.domain.constant.TossRewardRequestStatusType;
import com.ebaykorea.payback.core.dto.event.toss.TossRewardRequestDto;
import com.ebaykorea.payback.config.properties.SaturnApplicationProperties;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.domain.entity.event.request.TossRewardRequestResult;
import com.ebaykorea.payback.core.repository.TossRewardRequestRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.TossRewardRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.TossRewardRequestStatusRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper.TossRewardRequestMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TossRewardRequestRepositoryImpl implements TossRewardRequestRepository {
  private final TossRewardRepository repository;
  private final TossRewardRequestStatusRepository statusRepository;

  private final TossRewardRequestMapper mapper;
  private final SaturnApplicationProperties tenantProperties;

  @Transactional
  @Override
  public long save(final TossRewardRequestDto request) {
    final var requestNo = repository.getNextRequestNo();

    save(requestNo, request);
    saveStatus(requestNo, TossRewardRequestStatusType.Created);

    return requestNo;
  }

  @Override
  public Optional<TossRewardRequestResult> find(final TossRewardRequestDto request) {
    return repository.findByRequestIdAndUserTokenAndEventType(request.getRequestId(), request.getUserToken(), EventType.Toss)
        .map(mapper::map);
  }

  private void save(final long requestNo, final TossRewardRequestDto request) {
    final var entity = mapper.map(requestNo, tenantProperties.getTenantId(), request);
    repository.save(entity);
  }

  @Override
  public void saveStatus(final Long requestNo, final TossRewardRequestStatusType statusType) {
    final var statusEntity = mapper.map(requestNo, statusType);
    statusRepository.save(statusEntity);
  }
}
