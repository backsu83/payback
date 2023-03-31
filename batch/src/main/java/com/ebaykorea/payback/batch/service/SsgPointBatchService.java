package com.ebaykorea.payback.batch.service;

import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.API_GATEWAY_002;
import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.API_GATEWAY_003;
import static com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode.DOMAIN_SSG_ENTITY_005;
import static com.ebaykorea.payback.batch.util.PaybackDateTimes.DATE_TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.batch.util.PaybackInstants.now;

import com.ebaykorea.payback.batch.config.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointStatusType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import com.ebaykorea.payback.batch.domain.exception.PaybackException;
import com.ebaykorea.payback.batch.job.mapper.SsgPointCancelProcesserMapper;
import com.ebaykorea.payback.batch.job.mapper.SsgPointEarnProcesserMapper;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.payback.batch.util.support.CryptoAES256;
import com.ebaykorea.payback.batch.util.support.CryptoArche;
import com.google.common.base.CharMatcher;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointBatchService {

  private final SsgPointApiClient ssgPointApiClient;
  private final SmileClubApiClient smileClubApiClient;
  private final SsgTokenRepository ssgTokenRepository;
  private final SsgPointEarnProcesserMapper ssgPointEarnProcesserMapper;
  private final SsgPointCancelProcesserMapper ssgPointCancelProcesserMapper;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;

  public SsgPointTargetDto earn(final SsgPointProcesserDto item, SsgPointCertifier certifier) {
//    final var cardNo = getCardNo(item.getBuyerId(), item.getSiteType(), certifier);
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey());
    var request = ssgPointEarnProcesserMapper.mapToRequest(item, certifier, tokenId, "Tkwmnpj2FqYDn4FN82i8thYJUs5Eu1xhFaUAgRYakC4=");
    final var response = ssgPointApiClient.earnPoint(request);
    return ssgPointEarnProcesserMapper.mapToTarget(request, response, item);
  }

  public SsgPointTargetDto cancel(final SsgPointProcesserDto item, SsgPointCertifier certifier) {
//    final var cardNo = getCardNo(item.getBuyerId(), item.getSiteType(), certifier);
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey());
    var request = ssgPointCancelProcesserMapper.mapToRequest(item, certifier, tokenId, "Tkwmnpj2FqYDn4FN82i8thYJUs5Eu1xhFaUAgRYakC4=");
    final var response = ssgPointApiClient.cancelPoint(request);
    return ssgPointCancelProcesserMapper.mapToTarget(request, response, item);
  }

  public String getCardNo(final String buyerId, OrderSiteType siteType, SsgPointCertifier auth) {
    try {
      final var cardNo = smileClubApiClient.getCardNo(buyerId, auth.getMemberKey())
          .orElseThrow(() -> new PaybackException(API_GATEWAY_003 , buyerId))
          .getCardNo();
      final var toSiteType =  siteType.toString().toLowerCase();
      final var decryptCardNo=CharMatcher.anyOf(CryptoArche.decrypt(cardNo, toSiteType)).removeFrom("-");
      final var encryptCardNo = CryptoAES256.decrypt(decryptCardNo, auth.getEncryptKey(), auth.getEncryptIv());
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

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public long updateProcesserFail(final long orderNo, final String orderSiteType, final String tradeType) {
    return ssgPointTargetRepositorySupport.updateFailBy(orderNo , orderSiteType , tradeType);
  }

  @Transactional
  public long updateWriterSuceess(final SsgPointTargetDto item) {
    return ssgPointTargetRepositorySupport.updateSuceessBy(item.getOrderNo() ,
        item.getBuyerId() ,
        item.getSiteType(),
        item.getTradeType(),
        item.getAccountDate(),
        DATE_TIME_STRING_FORMATTER.parse(item.getRequestDate() , Instant::from),
        item.getResponseCode(),
        item.getPntApprId(),
        item.getSaveAmount()
    );
  }
}
