package com.ebaykorea.payback.batch.service;

import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.API_GATEWAY_002;
import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.API_GATEWAY_003;
import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_004;
import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_005;
import static com.ebaykorea.payback.batch.util.PaybackInstants.now;

import com.ebaykorea.payback.batch.config.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.config.properties.SsgPointAuthProperties;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.exception.PaybackException;
import com.ebaykorea.payback.batch.job.processer.SsgPointProcesserMapper;
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.payback.batch.util.support.CryptoAES256;
import com.ebaykorea.payback.batch.util.support.CryptoArche;
import com.ebaykorea.payback.batch.util.support.GsonUtils;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SsgPointBatchService {

  private final SsgPointApiClient ssgPointApiClient;
  private final SmileClubApiClient smileClubApiClient;
  private final SsgTokenRepository ssgTokenRepository;
  private final SsgPointProcesserMapper ssgPointProcesserMapper;
  private final SsgPointAuthProperties authProperties;

  public void earn(final SsgPointProcesserDto item) {
    final var authInfo = authProperties.getGmarket();
    System.out.println("authInfo: " + GsonUtils.toJsonPretty(authInfo));
    try {
      //임시 테스트 Key (지마켓용)
      final var cardNo = "wWsEXZRf1ht3q3JOdunhyJUVR4mL8hNxGVj99ZP/MD8=";
//      final var tokenId = getSsgAuthToken(authInfo.getClientId() , authInfo.getApiKey());
      final var tokenId = "e7aceb052303931b27164a791815b3694d75251b5d8b";
      var request = ssgPointProcesserMapper.map(item , authInfo , tokenId , cardNo);
//      ssgPointApiClient.earnPoint(request);
      System.out.println("earn request: " + GsonUtils.toJsonPretty(request));
      final var resultEarn = ssgPointApiClient.earnPoint(request);
      System.out.println("earn result: " + GsonUtils.toJsonPretty(resultEarn));

    } catch (Exception ex) {
      //TODO 실패경우 DB 상태값 변경 - FAIL
      throw new PaybackException(DOMAIN_SSG_ENTITY_004);
    }
  }

  public String getCardNo(final String buyerId) {
    final var authInfo = authProperties.getGmarket();
    try {
      final var cardNo = smileClubApiClient.getCardNo(buyerId, "S001")
          .orElseThrow(() -> new PaybackException(API_GATEWAY_003 , buyerId))
          .getCardNo();
      final var encryptCardNo = CryptoAES256.decrypt(CryptoArche.decryptGmarket(cardNo) ,
          authInfo.getEncryptKey() ,
          authInfo.getEncryptIv());
      return encryptCardNo;
    } catch (Exception ex) {
      //TODO 실패경우 카드번호 암복화 실패
      throw new PaybackException(DOMAIN_SSG_ENTITY_005);
    }
  }

  /**
   * ssgpoint tokenid 조회
   * 최대 1일 사용가능
   * 로컬캐시 갱신 시간은 1시간
   * @return
   */
  @Cacheable(cacheNames = "COMMON_KEY", key = "#name")
  public String getSsgAuthToken(String clientId , String apiKey) {
    SsgTokenEntity entity = ssgTokenRepository.findTopByExpireDateAfterOrderByExpireDateDesc(now());
    System.out.println("getSsgAuthToken : " + GsonUtils.toJsonPretty(entity));
    if(Objects.nonNull(entity)) {
      return entity.getTokenKey();
    } else {
      final var tokenInfo = ssgPointApiClient.getAuthToken(SsgPointAuthTokenRequest.builder()
          .clientId(clientId)
          .apiKey(apiKey)
          .build())
          .orElseThrow(() -> new PaybackException(API_GATEWAY_002 , "토큰 조회 실패"));
      saveSsgAuthToken(tokenInfo.getTokenId());
      return tokenInfo.getTokenId();
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
