package com.ebaykorea.payback.schedulercluster.service.member;

import static com.ebaykorea.payback.schedulercluster.config.cache.LocalCacheType.LocalCacheNames.USER_KEY;
import static com.ebaykorea.payback.schedulercluster.model.constant.TenantCode.GMARKET_TENANT;

import com.ebaykorea.payback.schedulercluster.client.QuiltApiClient;
import com.ebaykorea.payback.schedulercluster.client.dto.member.QuiltBaseResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(GMARKET_TENANT)
@Slf4j
@Service
@RequiredArgsConstructor
public class GmarketMemberService implements MemberService {

  private final QuiltApiClient quiltApiClient;
  private final ExecutorService taskExecutor;

  @Override
  @Cacheable(cacheNames = USER_KEY)
  public CompletableFuture<String> findSmileUserKeyAsync(final String memberKey) {
    return CompletableFuture.supplyAsync(() -> quiltApiClient.findSmileUserKeyByCustNo(memberKey), taskExecutor)
        .thenApply(result -> result
            .flatMap(QuiltBaseResponse::findSuccessData)
            .orElseThrow(() -> new RuntimeException("gmarket smileUserKey 없음")));
  }

}