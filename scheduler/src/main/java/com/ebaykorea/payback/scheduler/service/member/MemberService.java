package com.ebaykorea.payback.scheduler.service.member;

import static com.ebaykorea.payback.scheduler.config.cache.LocalCacheType.LocalCacheNames.USER_KEY;

import com.ebaykorea.payback.scheduler.client.QuiltApiClient;
import com.ebaykorea.payback.scheduler.client.dto.member.QuiltBaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final QuiltApiClient quiltApiClient;

  @Cacheable(cacheNames = USER_KEY)
  public String findSmileUserKey(final String memberKey) {
    return quiltApiClient.findSmileUserKey(memberKey)
        .flatMap(QuiltBaseResponse::findSuccessData)
        .orElseThrow(() -> new RuntimeException("smileUserKey 없음"));
  }
}
