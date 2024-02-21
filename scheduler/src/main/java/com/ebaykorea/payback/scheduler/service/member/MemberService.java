package com.ebaykorea.payback.scheduler.service.member;

import static com.ebaykorea.payback.scheduler.config.cache.LocalCacheType.LocalCacheNames.USER_KEY;

import com.ebaykorea.payback.scheduler.client.QuiltApiClient;
import com.ebaykorea.payback.scheduler.client.dto.member.QuiltBaseResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

  private final QuiltApiClient quiltApiClient;
  private final ExecutorService taskExecutor;

  @Cacheable(cacheNames = USER_KEY)
  public CompletableFuture<String> findSmileUserKeyAsync(final String memberKey) {
    return CompletableFuture.supplyAsync(() -> quiltApiClient.findSmileUserKey(memberKey), taskExecutor)
        .thenApply(result -> result
            .flatMap(QuiltBaseResponse::findSuccessData)
            .orElseThrow(() -> new RuntimeException("smileUserKey 없음")));
  }
}
