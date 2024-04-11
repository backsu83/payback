package com.ebaykorea.payback.schedulercluster.service;

import com.ebaykorea.payback.schedulercluster.client.SmileCashApiClient;
import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.MassSaveResponseDto;
import com.ebaykorea.payback.schedulercluster.mapper.MassSaveMapper;
import com.ebaykorea.payback.schedulercluster.model.MassSaveEvent;
import com.ebaykorea.payback.schedulercluster.repository.MassSaveRepository;
import com.ebaykorea.payback.schedulercluster.service.member.MemberService;
import com.ebaykorea.payback.schedulercluster.support.GsonUtils;
import com.ebaykorea.payback.schedulercluster.support.SchedulerUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MassSaveAplicationService {

  private final MemberService memberService;
  private final MassSaveRepository massSaveRepository;
  private final SmileCashApiClient smileCashApiClient;
  private final MassSaveMapper massSaveMapper;
  private final ExecutorService taskExecutor;

  public void requsetMassSave(final int maxRows, final int maxRetryCount) {
    final var targets = massSaveRepository.findTargets(maxRows , maxRetryCount);

    //TODO smile-pg api 변경 후 적용 가능 ( 1step )
    final var futures = targets
        .stream()
        .map(entity ->
            memberService.findSmileUserKeyAsync(entity.getMemberKey())
              .thenAcceptAsync(userkey ->
                  checkUserKeyThenRequestMassSave(entity, userkey),
                  taskExecutor)
              .exceptionally( ex-> {
                return null;
              }))
        .collect(Collectors.toUnmodifiableList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenAccept(v -> futures.forEach(CompletableFuture::join))
        .join();
  }

  private void checkUserKeyThenRequestMassSave(final MassSaveEvent entity, final String userkey) {
    if (SchedulerUtils.isBlank(userkey)) {
      massSaveRepository.updateRetryCount(entity);
    } else {
      smileCashApiClient.requestMassSave(massSaveMapper.map(entity), String.format("basic %s", userkey))
          .filter(MassSaveResponseDto::isSuccess)
          .ifPresentOrElse(
              response -> massSaveRepository.updateSaveStatus(userkey , entity),
              () -> massSaveRepository.updateRetryCount(entity));
    }
  }

}
