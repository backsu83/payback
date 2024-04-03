package com.ebaykorea.payback.schedulercluster.service;

import com.ebaykorea.payback.schedulercluster.service.mass.MassSaveService;
import com.ebaykorea.payback.schedulercluster.support.GsonUtils;
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

  private final MassSaveService massRequestService;

  public void requestMassSave(final int maxRows, final int maxRetryCount) {
    final var targets = massRequestService.findTargets(maxRows , maxRetryCount);
    System.out.println(GsonUtils.toJsonPretty(targets));

    //TODO smile-pg api 변경 후 적용 가능 ( 1step )
    final var futures = (List<CompletableFuture<Void>>) targets.stream()
        .map(entity -> massRequestService.update(entity))
        .collect(Collectors.toUnmodifiableList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        .thenAccept(v -> futures.forEach(CompletableFuture::join))
        .join();
  }

}
