package com.ebaykorea.payback.schedulercluster.config.cache;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LocalCacheType {

  public static class LocalCacheNames {
    public static final String USER_KEY = "USER_KEY";
  }

  @Getter
  @RequiredArgsConstructor
  public enum LocalCache {
    USER_KEY(LocalCacheNames.USER_KEY, 10L, TimeUnit.SECONDS);

    private final String cacheName;
    private final long ttl;
    private final TimeUnit ttlTimeUnit;
  }
}
