package com.ebaykorea.payback.schedulercluster.repository;

import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient;
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.schedulercluster.config.FusionClusterProperties;
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper;
import com.ebaykorea.payback.schedulercluster.repository.stardb.SmileCashEventRepository;
import com.ebaykorea.payback.schedulercluster.repository.stardb.entity.SmileCashEventEntity;
import com.ebaykorea.payback.schedulercluster.service.member.MemberService;
import com.ebaykorea.payback.schedulercluster.support.SchedulerUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Slf4j
@Service
@RequiredArgsConstructor
public class GmarketMassSaveRepository implements MassSaveRepository {

  private final SmileCashEventRepository repository;
  private final MemberService memberService;
  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveMapper massSaveRequestMapper;
  private final ExecutorService taskExecutor;
  private final FusionClusterProperties properties;
  private static final int MASS_SAVE_REQUEST_STATUS = 0;
  private static final int MASS_SAVE_COMPLETE_STATUS = 50;

  @Override
  public List<SmileCashEventEntity> findTargets(final int maxRows, final int maxRetryCount) {
    return repository.findTargets(maxRows, MASS_SAVE_REQUEST_STATUS, maxRetryCount, properties.getMod(), properties.getModCount());
  }

  @Override
  public CompletableFuture<Void> update(final Object object) {
    final var entity = (SmileCashEventEntity) object;
    final var result =  memberService.findSmileUserKeyByCustNo(entity.getCustNo())
        .thenAcceptAsync(userKey -> {
          if (SchedulerUtils.isBlank(userKey)) {
            failed(entity);
          } else {
            smileCashApiClient.requestMassSave(massSaveRequestMapper.map(entity), String.format("basic %s", userKey))
                .filter(MassSaveResponseDto::isSuccess)
                .ifPresentOrElse(success -> success(entity), () -> failed(entity));
          }
        }, taskExecutor)
        .exceptionally( ex-> {
          return null;
        });
    return result;
  }

  private void success(final SmileCashEventEntity entity) {
    repository.update(entity.getSmilePayNo() , MASS_SAVE_COMPLETE_STATUS, 0 , entity.getRegId());
  }

  private void failed(final SmileCashEventEntity entity) {
    repository.update(entity.getSmilePayNo(), entity.getApprovalStatus(), entity.getTryCount() + 1, entity.getRegId());
  }

}
