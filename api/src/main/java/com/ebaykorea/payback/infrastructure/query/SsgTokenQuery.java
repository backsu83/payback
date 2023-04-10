package com.ebaykorea.payback.infrastructure.query;

import com.ebaykorea.payback.core.service.SsgPointStateDelegate;
import com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static com.ebaykorea.payback.util.PaybackInstants.now;

@Service
@RequiredArgsConstructor
public class SsgTokenQuery {

  private final SsgTokenRepository ssgTokenRepository;
  private final SsgPointApiClient ssgPointApiClient;
  private final SsgPointStateDelegate ssgPointStateDelegate;

  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  public String getSsgAuthToken() {
    SsgTokenEntity entity = ssgTokenRepository.findTopByExpireDateAfterOrderByExpireDateDesc(now());
    return entity.getTokenKey();

//    if(Objects.nonNull(entity)) {
//    }
//    else {
//      final var authInfo = ssgPointStateDelegate.find(OrderSiteType.Gmarket).auth();
//      final var tokenInfo = ssgPointApiClient.getAuthToken(
//              SsgPointAuthTokenRequestDto.builder()
//                      .clientId(authInfo.getClientId())
//                      .apiKey(authInfo.getApiKey())
//                      .build()
//      );
//      saveSsgAuthToken(tokenInfo.getTokenId());
//      return tokenInfo.getTokenId();
//    }
  }

  @Transactional
  public void saveSsgAuthToken(String tokenKey) {
    ssgTokenRepository.save(SsgTokenEntity.builder()
        .tokenKey(tokenKey)
        .expireDate(now().plus(Duration.ofDays(1)))
        .build());
  }
}
