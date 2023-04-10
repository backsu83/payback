package com.ebaykorea.payback.config.cache;

import com.ebaykorea.payback.config.cache.LocalCacheType.LocalCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var caches = Arrays.stream(LocalCache.values())
                .map(cache -> new CaffeineCache(cache.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(cache.getTtl(), cache.getTtlTimeUnit())
                                .build())
                )
                .collect(Collectors.toUnmodifiableList());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
