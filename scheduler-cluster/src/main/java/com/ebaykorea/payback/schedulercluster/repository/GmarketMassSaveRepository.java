package com.ebaykorea.payback.schedulercluster.repository;

import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.schedulercluster.config.FusionClusterProperties;
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveEventMapper;
import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent;
import com.ebaykorea.payback.schedulercluster.repository.stardb.SmileCashEventRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Slf4j
@Service
@RequiredArgsConstructor
public class GmarketMassSaveRepository implements MassSaveRepository {

  private final SmileCashEventRepository repository;
  private final MassSaveEventMapper mapper;
  private final FusionClusterProperties properties;
  private static final int MASS_SAVE_REQUEST_STATUS = 0;
  private static final int MASS_SAVE_COMPLETE_STATUS = 40;

  @Override
  public List<MassSaveEvent> findTargets(final int maxRows, final int maxRetryCount) {
    return repository.findTargets(maxRows , MASS_SAVE_REQUEST_STATUS , maxRetryCount , properties.getMod() , properties.getModCount())
        .stream()
        .map(mapper::map)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public void updateSaveStatus(final String userKey, final MassSaveEvent entity) {
    repository.update(Long.valueOf(entity.getShopTransactionId()) , MASS_SAVE_COMPLETE_STATUS, 0 , entity.getOperator());
  }

  @Override
  public void updateRetryCount(final MassSaveEvent entity) {
    repository.update(Long.valueOf(entity.getShopTransactionId()), entity.getStatus(), entity.getRetryCount() + 1, entity.getOperator());
  }

}
