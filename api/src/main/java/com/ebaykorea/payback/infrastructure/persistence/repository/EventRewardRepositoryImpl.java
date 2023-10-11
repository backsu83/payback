package com.ebaykorea.payback.infrastructure.persistence.repository;


import com.ebaykorea.payback.config.properties.SaturnApplicationProperties;
import com.ebaykorea.payback.core.domain.constant.EventRequestStatusType;
import com.ebaykorea.payback.core.domain.constant.EventType;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDetailDto;
import com.ebaykorea.payback.core.dto.event.EventRewardRequestDto;
import com.ebaykorea.payback.core.repository.EventRewardRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.EventRewardRequestDetailRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.EventRewardRequestRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.EventRewardRequestStatusRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.event.mapper.EventRewardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventRewardRepositoryImpl implements EventRewardRepository {
  private final EventRewardRequestRepository repository;
  private final EventRewardRequestDetailRepository detailRepository;
  private final EventRewardRequestStatusRepository statusRepository;

  private final EventRewardMapper mapper;
  private final SaturnApplicationProperties tenantProperties;

  @Transactional
  @Override
  public long save(final EventRewardRequestDto request) {
    final var requestNo = repository.getNextRequestNo();

    save(requestNo, request);
    saveDetails(requestNo, request.getDetails());
    saveStatus(requestNo, request.getEventType(), EventRequestStatusType.Created);

    return requestNo;
  }

  @Override
  public boolean alreadySaved(final String requestId, final EventType eventType) {
    return repository.findByRequestIdAndEventType(requestId, eventType)
        .isPresent();
  }

  private void save(final long requestNo, final EventRewardRequestDto request) {
    final var entity = mapper.map(requestNo, tenantProperties.getTenantId(), request);
    repository.save(entity);
  }

  private void saveDetails(final long requestNo, final List<EventRewardRequestDetailDto> details) {
    final var detailEntities = details.stream()
        .map(detail -> {
          final var seq = detailRepository.getNextSeq();
          return mapper.map(seq, requestNo, detail);
        })
        .collect(Collectors.toUnmodifiableList());

    detailRepository.saveAll(detailEntities);
  }

  @Override
  public void saveStatus(final Long requestNo, final EventType eventType, final EventRequestStatusType statusType) {
    final var statusEntity = mapper.map(requestNo, eventType, statusType);
    statusRepository.save(statusEntity);
  }
}
