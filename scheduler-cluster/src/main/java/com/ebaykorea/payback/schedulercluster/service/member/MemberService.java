package com.ebaykorea.payback.schedulercluster.service.member;

import static com.ebaykorea.payback.schedulercluster.config.cache.LocalCacheType.LocalCacheNames.USER_KEY;

import com.ebaykorea.payback.schedulercluster.client.QuiltApiClient;
import com.ebaykorea.payback.schedulercluster.client.dto.member.QuiltBaseResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
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
  public CompletableFuture<String> findSmileUserKeyByCustNo(final String memberKey) {
    return findSmileUserKeyAsync(memberKey, quiltApiClient::findSmileUserKeyByCustNo);
  }

  @Cacheable(cacheNames = USER_KEY)
  public CompletableFuture<String> findSmileUserKeyByMemberId(final String memberKey) {
    return findSmileUserKeyAsync(memberKey, quiltApiClient::findSmileUserKeyByMemberId);
  }

  private CompletableFuture<String> findSmileUserKeyAsync(final String memberKey, final Function<String, Optional<QuiltBaseResponse<String>>> quilt) {
    return CompletableFuture.supplyAsync(
        () -> quilt.apply(memberKey)
                .flatMap(QuiltBaseResponse::findSuccessData)
                .orElseThrow(() -> new RuntimeException("smileUserKey 없음"))
        , taskExecutor);
  }
}
