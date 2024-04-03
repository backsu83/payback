package com.ebaykorea.payback.schedulercluster.service.mass;

import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient;
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper;
import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.schedulercluster.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.schedulercluster.service.member.MemberService;
import com.ebaykorea.payback.schedulercluster.support.SchedulerUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(AUCTION_TENANT)
@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionMassSaveService implements MassSaveService {

  private final SmileCashSaveQueueRepository repository;
  private final MemberService memberService;
  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveMapper massSaveRequestMapper;
  private final ExecutorService taskExecutor;
  private static final int MASS_SAVE_REQUEST_STATUS = 3;
  private static final int MASS_SAVE_COMPLETE_STATUS = 1;

  @Override
  public List<SmileCashSaveQueueEntity> findTargets(final int maxRows, final int maxRetryCount) {
    return repository.findTargets(maxRows , MASS_SAVE_REQUEST_STATUS , maxRetryCount);
  }

  @Override
  public CompletableFuture<Void> update(final Object object) {
    final var entity = (SmileCashSaveQueueEntity) object;
    final var result =  memberService.findSmileUserKeyByMemberId(entity.getMemberId())
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

  private void success(final SmileCashSaveQueueEntity entity) {
    repository.update(entity.getSeqNo() , MASS_SAVE_COMPLETE_STATUS, 0 , entity.getInsertOperator());
  }

  private void failed(final SmileCashSaveQueueEntity entity) {
    repository.update(entity.getSeqNo(), entity.getSaveStatus(), entity.getRetryCount() + 1, entity.getInsertOperator());
  }
}
