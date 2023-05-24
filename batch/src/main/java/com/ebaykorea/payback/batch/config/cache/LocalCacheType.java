package com.ebaykorea.payback.batch.config.cache;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LocalCacheType {

  /**
   * 공통 로컬캐시 1분
   * SSG 토큰 캐시 1시간
   */
  public static class LocalCacheNames {
    public static final String COMMON_KEY = "COMMON_KEY";
    public static final String SSG_TOKEN_KEY = "SSG_TOKEN_KEY";
  }

  @Getter
  @RequiredArgsConstructor
  public enum LocalCache {
    COMMON_KEY(LocalCacheNames.COMMON_KEY, 60 * 1L, TimeUnit.SECONDS),
    SSG_TOKEN_KEY(LocalCacheNames.SSG_TOKEN_KEY, 60 * 60 * 1L, TimeUnit.SECONDS);

    private final String cacheName;
    private final long ttl;
    private final TimeUnit ttlTimeUnit;

  }
}
