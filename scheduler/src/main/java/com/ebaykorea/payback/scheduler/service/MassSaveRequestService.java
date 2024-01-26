package com.ebaykorea.payback.scheduler.service;

import static com.ebaykorea.payback.scheduler.model.constant.TenantCode.AUCTION_TENANT;

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SaveResultResponseDto;
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.scheduler.service.member.MemberService;
import com.ebaykorea.payback.scheduler.service.smilecash.SmileCashApprovalService;
import com.ebaykorea.payback.scheduler.support.SchedulerUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
  private final SmileCashApprovalService smileCashApprovalService;

  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveRequestMapper massSaveRequestMapper;
  private final ExecutorService taskExecutor;

  private static final int MASS_SAVE_REQUEST_STATUS = 3;
  private static final int MASS_SAVE_REQUESTED_STATUS = 4;

  public void requestMassSave(final int maxRows, final int maxRetryCount) {
    final var massSaveTargets = repository.findTargets(maxRows, MASS_SAVE_REQUEST_STATUS, maxRetryCount);

    final var futures = massSaveTargets.stream().map(entity ->
        findSmileUserKeyAsync(entity)
            .thenAcceptAsync(userKey -> {
              if (SchedulerUtils.isBlank(userKey)) {
                log.info("userKey 없음. seqNo: {}, txId: {}, memberId: {}", entity.getSeqNo(), entity.getTxId(), entity.getMemberId());
                setFailed(entity);
              } else {
                requestMassSaveThenUpdateResult(entity, userKey);
              }
            }, taskExecutor)
            .exceptionally(ex -> {
              log.error("스마일캐시 대량 적립 요청 오류. seqNo: {}, txId: {}, message: {}", entity.getSeqNo(), entity.getTxId(), ex.getMessage());
              setFailed(entity);
              return null;
            })
    );

    futures.parallel().forEach(CompletableFuture::join);
  }

  private void requestMassSaveThenUpdateResult(final SmileCashSaveQueueEntity entity, final String userKey) {
    smileCashApiClient.requestMassSave(massSaveRequestMapper.map(entity), String.format("basic %s", userKey))
        .filter(MassSaveResponseDto::isFailed)
        .ifPresentOrElse(
            // 실패
            failed -> {
              log.info("requestMassSave response FAILED seqNo: {}, txId: {}, returnCode: {}, errorMessage: {}",
                  entity.getSeqNo(), entity.getTxId(), failed.getReturnCode(), failed.getErrorMessage());
              setFailed(entity);
            },
            // 성공
            () -> {
              log.info("requestMassSave response SUCCESS seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              repository.update(entity.getSeqNo(), MASS_SAVE_REQUESTED_STATUS, 0, entity.getInsertOperator());
            });
  }

  public void checkSmileCashStatusThenUpdateResult(final int maxRows, final int maxRetryCount) {
    final var checkStatusTargets = repository.findTargets(maxRows, MASS_SAVE_REQUESTED_STATUS, maxRetryCount);

    final var futures = checkStatusTargets.stream().map(entity ->
        findSmileUserKeyAsync(entity)
            .thenAcceptAsync(userKey -> {
              if (SchedulerUtils.isBlank(userKey)) {
                log.info("userKey 없음. seqNo: {}, txId: {}, memberId: {}", entity.getSeqNo(), entity.getTxId(), entity.getMemberId());
                setFailed(entity);
              } else {
                // 대량 적립 요청건 적립 여부 확인 및 결과 저장
                findMassRequestedThenUpdateResult(entity, userKey);
              }
            }, taskExecutor)
            .exceptionally(ex -> {
              log.error("스마일캐시 상태 조회 오류. seqNo: {}, txId: {}, message: {}", entity.getSeqNo(), entity.getTxId(), ex.getMessage());
              setFailed(entity);
              return null;
            })
    );

    futures.parallel().forEach(CompletableFuture::join);
  }

  private void findMassRequestedThenUpdateResult(final SmileCashSaveQueueEntity entity, final String userKey) {
    smileCashApiClient.findSaveResult(massSaveRequestMapper.mapToSaveResultRequest(entity), String.format("basic %s", userKey))
        .filter(SaveResultResponseDto::isSaved)
        .ifPresentOrElse(
            success -> {
              log.info("checkMassSaveStatus response SAVED seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              smileCashApprovalService.setApproved(success.getResult(), userKey, entity);
            },
            () -> {
              log.info("checkMassSaveStatus response NOT SAVED seqNo: {}, txId: {}", entity.getSeqNo(), entity.getTxId());
              setFailed(entity);
            });
  }

  private CompletableFuture<String> findSmileUserKeyAsync(final SmileCashSaveQueueEntity entity) {
    return memberService.findSmileUserKeyAsync(entity.getMemberId())
        .exceptionally(ex -> {
          log.error("findSmileUserKey 오류. seqNo: {}, txId: {}, memberId: {}, message: {}", entity.getSeqNo(), entity.getTxId(), entity.getMemberId(), ex.getMessage());
          return "";
        });
  }

  private void setFailed(final SmileCashSaveQueueEntity entity) {
    repository.update(entity.getSeqNo(), entity.getSaveStatus(), entity.getRetryCount() + 1, entity.getInsertOperator());
  }
}
