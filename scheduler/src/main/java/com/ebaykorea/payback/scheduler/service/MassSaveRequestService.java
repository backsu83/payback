package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultResponseDto;
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper;
import com.ebaykorea.payback.scheduler.mapper.SmileCashSaveMapper;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.scheduler.service.member.MemberService;
import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MassSaveRequestService {

  private final SmileCashSaveQueueRepository repository;
  private final MemberService memberService;
  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveRequestMapper massSaveRequestMapper;
  private final SmileCashSaveMapper smileCashSaveMapper;

  private static final int MASS_SAVED = 1;
  private static final int MASS_SAVE_REQUEST_STATUS = 3;
  private static final int MASS_SAVE_REQUESTED_STATUS = 4;

  public void requestMassSave(final int maxRows, final int maxRetryCount) {
    final var massSaveTargets = repository.findTargets(maxRows, MASS_SAVE_REQUEST_STATUS, maxRetryCount);

    massSaveTargets.forEach(entity -> Optional.of(memberService.findSmileUserKey(entity.getMemberId()))
        .filter(SchedulerUtils::isNotBlank)
        .ifPresentOrElse(
            userKey -> {
              try {
                requestMassSaveThenUpdate(entity, userKey);
              } catch (Exception ex) {
                log.error("requestMassSaveThenUpdate error. {}", ex.getMessage());
              }
            },
            () -> makeFailed(entity)
        )
    );
  }

  private void requestMassSaveThenUpdate(final SmileCashSaveQueueEntity entity, final String smileUserKey) {
    CompletableFuture.supplyAsync(
        () -> smileCashApiClient.requestMassSave(massSaveRequestMapper.map(entity), String.format("basic %s", smileUserKey)))
        .thenAccept(response -> response.filter(MassSaveResponseDto::isFailed)
            .ifPresentOrElse(
                // 실패
                failed -> {
                  log.info("requestMassSave response FAILED seqNo: {}, txId: {}, returnCode: {}, errorMessage: {}", entity.getSeqNo(), entity.getTxId(), failed.getReturnCode(), failed.getErrorMessage());
                  makeFailed(entity);
                },
                // 성공
                () -> {
                  log.info("requestMassSave response SUCCESS seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
                  repository.update(entity.getSeqNo(), MASS_SAVE_REQUESTED_STATUS, 0, entity.getInsertOperator());
                }
            ));
  }

  public void checkSmileCashStatusThenUpdateResult(final int maxRows, final int maxRetryCount) {
    final var checkStatusTargets = repository.findTargets(maxRows, MASS_SAVE_REQUESTED_STATUS, maxRetryCount);

    checkStatusTargets.forEach(entity -> Optional.of(memberService.findSmileUserKey(entity.getMemberId()))
        .filter(SchedulerUtils::isNotBlank)
        .ifPresentOrElse(
            userKey -> {
              try {
                checkMassSaveStatusThenApproved(entity, userKey);
              } catch (Exception ex) {
                log.error("checkMassSaveStatusThenApproved error. {}", ex.getMessage());
              }
              },
            () -> makeFailed(entity)
        )
    );
  }

  private void checkMassSaveStatusThenApproved(final SmileCashSaveQueueEntity entity, final String smileUserKey) {
    CompletableFuture.supplyAsync(
        () -> smileCashApiClient.findSaveResult(massSaveRequestMapper.mapToSaveResultRequest(entity), String.format("basic %s", smileUserKey)))
        .thenAccept(response -> response.filter(SaveResultResponseDto::isSaved)
            .ifPresentOrElse(
                success -> {
                  log.info("checkMassSaveStatus response SUCCESS seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
                  repository.saveApproval(smileCashSaveMapper.map(success.getResult(), smileUserKey, entity));
                  repository.update(entity.getSeqNo(), MASS_SAVED, entity.getRetryCount(), entity.getInsertOperator());
                },
                () -> {
                  log.info("checkMassSaveStatus response NOT SAVED seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
                  makeFailed(entity);
                }
            ));
  }

  private void makeFailed(final SmileCashSaveQueueEntity entity) {
    repository.update(entity.getSeqNo(), entity.getSaveStatus(), entity.getRetryCount() + 1, entity.getInsertOperator());
  }
}
