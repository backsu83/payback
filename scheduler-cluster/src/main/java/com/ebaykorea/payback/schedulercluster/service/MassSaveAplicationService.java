package com.ebaykorea.payback.schedulercluster.service;

import com.ebaykorea.payback.schedulercluster.repository.MassSaveRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MassSaveAplicationService {

  private final MassSaveRepository massSaveRepository;

  public void requestMassSave(final int maxRows, final int maxRetryCount) {
    final var targets = massSaveRepository.findTargets(maxRows , maxRetryCount);

    //TODO smile-pg api 변경 후 적용 가능 ( 1step )
    final var futures = (List<CompletableFuture<Void>>) targets.stream()
        .map(entity -> massSaveRepository.update(entity))
        .collect(Collectors.toUnmodifiableList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenAccept(v -> futures.forEach(CompletableFuture::join))
        .join();
  }

}
