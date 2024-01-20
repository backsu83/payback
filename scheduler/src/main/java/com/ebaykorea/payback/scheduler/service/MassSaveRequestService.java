package com.ebaykorea.payback.scheduler.service;

import com.ebaykorea.payback.scheduler.client.SmileCashApiClient;
import com.ebaykorea.payback.scheduler.client.dto.smilecash.SmileCashResponseDto;
import com.ebaykorea.payback.scheduler.mapper.MassSaveRequestMapper;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.SmileCashSaveQueueRepository;
import com.ebaykorea.payback.scheduler.repository.maindb2ex.entity.SmileCashSaveQueueEntity;
import com.ebaykorea.payback.scheduler.service.member.MemberService;
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
  private final MassSaveRequestMapper mapper;

  private static final int MASS_SAVE_REQUEST_STATUS = 3;
  private static final int MASS_SAVE_REQUESTED_STATUS = 4;

  public void run(final int maxRows, final int maxRetryCount) {
    final var massSaveTargets = repository.findTargets(maxRows, MASS_SAVE_REQUEST_STATUS, maxRetryCount);

    massSaveTargets.forEach(entity -> {
      final var userKey = memberService.findSmileUserKey(entity.getMemberId());
      requestMassSaveThenUpdate(entity, userKey);
    });
  }

  private void requestMassSaveThenUpdate(final SmileCashSaveQueueEntity entity, final String smileUserKey) {
    CompletableFuture.supplyAsync(() -> smileCashApiClient.requestMassSave(mapper.map(entity), String.format("basic %s", smileUserKey)))
        .thenAcceptAsync(response -> response.filter(SmileCashResponseDto::isSuccess)
            .ifPresentOrElse(
                // 성공
                success -> {
                  log.info("requestMassSave response SUCCESS seqNo: {}", entity.getSeqNo());
                  repository.update(entity.getSeqNo(), MASS_SAVE_REQUESTED_STATUS, entity.getRetryCount(), entity.getInsertOperator());
                },
                // 실패
                () -> {
                  log.info("requestMassSave response ERROR seqNo: {}", entity.getSeqNo());
                  repository.update(entity.getSeqNo(), entity.getSaveStatus(), entity.getRetryCount() + 1, entity.getInsertOperator());
                }
            ));
  }
}
