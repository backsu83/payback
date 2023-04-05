package com.ebaykorea.payback.batch.config.cache;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LocalCacheType {

  public static class LocalCacheNames {
    public static final String COMMON_KEY = "COMMON_KEY";
  }

  @Getter
  @RequiredArgsConstructor
  public enum LocalCache {
    COMMON_KEY(LocalCacheNames.COMMON_KEY, 60 * 1L, TimeUnit.SECONDS);

    private final String cacheName;
    private final long ttl;
    private final TimeUnit ttlTimeUnit;

  }
}
