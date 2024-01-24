package com.ebaykorea.payback.scheduler.service;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultResponseDto;
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper;
import com.ebaykorea.payback.scheduler.mapper.SmileCashSaveMapper;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.scheduler.service.member.MemberService;
import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
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
public class MassSaveRequestService {

  private final SmileCashSaveQueueRepository repository;
  private final MemberService memberService;
  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveRequestMapper massSaveRequestMapper;
  private final SmileCashSaveMapper smileCashSaveMapper;
  private final ExecutorService taskExecutor;

  private static final int MASS_SAVED = 1;
  private static final int MASS_SAVE_REQUEST_STATUS = 3;
  private static final int MASS_SAVE_REQUESTED_STATUS = 4;

  public void requestMassSave(final int maxRows, final int maxRetryCount) {
    final var massSaveTargets = repository.findTargets(maxRows, MASS_SAVE_REQUEST_STATUS, maxRetryCount);

    massSaveTargets.forEach(entity ->
        findSmileUserKeyAsync(entity)
            .thenAcceptAsync(userKey -> {
              if (SchedulerUtils.isBlank(userKey)) {
                log.info("userKey 없음. seqNo: {}, memberId: {}", entity.getSeqNo(), entity.getMemberId());
                makeFailed(entity);
              } else {
                requestMassSaveThenUpdateResult(entity, userKey);
              }
            }, taskExecutor)
            .exceptionally(ex -> {
              log.error("requestMassSaveThenUpdate error. {}", ex.getMessage());
              return null;
            })
    );
  }

  private void requestMassSaveThenUpdateResult(final SmileCashSaveQueueEntity entity, final String userKey) {
    smileCashApiClient.requestMassSave(massSaveRequestMapper.map(entity), String.format("basic %s", userKey))
        .filter(MassSaveResponseDto::isFailed)
        .ifPresentOrElse(
            // 실패
            failed -> {
              log.info(
                  "requestMassSave response FAILED seqNo: {}, txId: {}, returnCode: {}, errorMessage: {}",
                  entity.getSeqNo(), entity.getTxId(), failed.getReturnCode(), failed.getErrorMessage());
              makeFailed(entity);
            },
            // 성공
            () -> {
              log.info("requestMassSave response SUCCESS seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              repository.update(entity.getSeqNo(), MASS_SAVE_REQUESTED_STATUS, 0, entity.getInsertOperator());
            });
  }

  public void checkSmileCashStatusThenUpdateResult(final int maxRows, final int maxRetryCount) {
    final var checkStatusTargets = repository.findTargets(maxRows, MASS_SAVE_REQUESTED_STATUS, maxRetryCount);

    checkStatusTargets.forEach(entity ->
        findSmileUserKeyAsync(entity)
            .thenAcceptAsync(userKey -> {
              if (SchedulerUtils.isBlank(userKey)) {
                log.info("userKey 없음. seqNo: {}, memberId: {}", entity.getSeqNo(), entity.getMemberId());
                makeFailed(entity);
              } else {
                // 대량 적립 요청건 적립 여부 확인 및 결과 저장
                findMassRequestedThenUpdateResult(entity, userKey);
              }
            }, taskExecutor)
            .exceptionally(ex -> {
              log.error("checkMassSaveStatusThenApproved error. {}", ex.getMessage());
              return null;
            })
    );
  }

  private void findMassRequestedThenUpdateResult(final SmileCashSaveQueueEntity entity,
      final String userKey) {
    smileCashApiClient.findSaveResult(massSaveRequestMapper.mapToSaveResultRequest(entity), String.format("basic %s", userKey))
        .filter(SaveResultResponseDto::isSaved)
        .ifPresentOrElse(
            success -> {
              log.info("checkMassSaveStatus response SUCCESS seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              repository.saveApproval(smileCashSaveMapper.map(success.getResult(), userKey, entity));
              repository.update(entity.getSeqNo(), MASS_SAVED, entity.getRetryCount(), entity.getInsertOperator());
            },
            () -> {
              log.info("checkMassSaveStatus response NOT SAVED seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              makeFailed(entity);
            });
  }

  private CompletableFuture<String> findSmileUserKeyAsync(final SmileCashSaveQueueEntity entity) {
    return CompletableFuture.supplyAsync(() -> memberService.findSmileUserKey(entity.getMemberId()), taskExecutor)
        .exceptionally(ex -> {
          log.info("findSmileUserKey 오류. seqNo: {}, memberId: {}, message: {}", entity.getSeqNo(), entity.getMemberId(), ex.getMessage());
          return "";
        });
  }

  private void makeFailed(final SmileCashSaveQueueEntity entity) {
    repository.update(entity.getSeqNo(), entity.getSaveStatus(), entity.getRetryCount() + 1, entity.getInsertOperator());
  }
}
