package com.ebaykorea.payback.infrastructure.query;

import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.payback.infrastructure.persistence.repository.opayreward.SsgTokenRepository;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SsgTokenQuery {

  private final SsgTokenRepository ssgTokenRepository;

  public String getSsgAuthToken() {
    SsgTokenEntity entity = ssgTokenRepository.findTopByExpireDateAfterOrderByExpireDateDesc(now());
    if(Objects.nonNull(entity)) {
      return entity.getTokenKey();
    } else {
      //토큰값 : ba49d3f7230232f00306468c2102975c9b845f73222b
      String tokenKey =" ba49d3f7230232f00306468c2102975c9b845f73222";
      saveSsgAuthToken(tokenKey);
      return tokenKey;
    }
  }

  @Transactional
  public void saveSsgAuthToken(String tokenKey) {
    ssgTokenRepository.save(SsgTokenEntity.builder()
        .tokenKey(tokenKey)
        .expireDate(now().plus(Duration.ofDays(1)))
        .build());
  }
}
