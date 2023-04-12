package com.ebaykorea.payback.batch.service;

import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_CARD_CRYPTO;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADD;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADDCNCL;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_TOKEN;
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
import com.ebaykorea.payback.batch.domain.exception.BatchProcesserException;
import com.ebaykorea.payback.batch.job.mapper.SsgPointCancelProcesserMapper;
import com.ebaykorea.payback.batch.job.mapper.SsgPointEarnProcesserMapper;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.payback.batch.util.support.CryptoAES256;
import com.ebaykorea.payback.batch.util.support.CryptoArche;
import com.google.common.base.CharMatcher;
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
    final var cardNo = "Tkwmnpj2FqYDn4FN82i8thYJUs5Eu1xhFaUAgRYakC4="; //임시카드번호
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey(), item.getSiteType());
    try {
      var request = ssgPointEarnProcesserMapper.mapToRequest(item, certifier, tokenId, cardNo);
      final var response = ssgPointApiClient.earnPoint(request);
      return ssgPointEarnProcesserMapper.mapToTarget(request, response, item);
    } catch (Exception e) {
      throw new BatchProcesserException(ERR_PNTADD);
    }
  }

  public SsgPointTargetDto cancel(final SsgPointProcesserDto item, SsgPointCertifier certifier) {
//    final var cardNo = getCardNo(item.getBuyerId(), item.getSiteType(), certifier);
    final var cardNo = "Tkwmnpj2FqYDn4FN82i8thYJUs5Eu1xhFaUAgRYakC4="; //임시카드번호
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey(), item.getSiteType());
    try {
      var request = ssgPointCancelProcesserMapper.mapToRequest(item, certifier, tokenId, cardNo);
      final var response = ssgPointApiClient.cancelPoint(request);
      return ssgPointCancelProcesserMapper.mapToTarget(request, response, item);
    } catch (Exception e) {
      throw new BatchProcesserException(ERR_PNTADDCNCL);
    }
  }

  public String getCardNo(final String buyerId, OrderSiteType siteType, SsgPointCertifier auth) {
    try {
      final var cardNo = smileClubApiClient.getCardNo(buyerId, auth.getMemberKey()).getCardNo();
      final var toSiteType =  siteType.toString().toLowerCase();
      final var decryptCardNo=CharMatcher.anyOf(CryptoArche.decrypt(cardNo, toSiteType)).removeFrom("-");
      final var encryptCardNo = CryptoAES256.decrypt(decryptCardNo, auth.getEncryptKey(), auth.getEncryptIv());
      return encryptCardNo;
    } catch (Exception ex) {
      throw new BatchProcesserException(ERR_CARD_CRYPTO);
    }
  }

  /**
   * ssgpoint tokenid 조회
   * 최대 1일 사용가능
   * @return
   */
  @Cacheable(cacheNames = "SSG_TOKEN_KEY", key = "#siteType.getShortCode()")
  public String getSsgAuthToken(String clientId , String apiKey, OrderSiteType siteType) {
    SsgTokenEntity entity = ssgTokenRepository.findTopBySiteTypeAndExpireDateAfterOrderByExpireDateDesc(siteType.getShortCode() ,now());
    if(Objects.nonNull(entity)) {
      return entity.getTokenKey();
    } else {
      try {
        final var tokenInfo = ssgPointApiClient.getAuthToken(SsgPointAuthTokenRequest.builder()
            .clientId(clientId)
            .apiKey(apiKey)
            .build());
        saveSsgAuthToken(tokenInfo.getTokenId() , siteType);
        return tokenInfo.getTokenId();
      } catch (Exception ex) {
        throw new BatchProcesserException(ERR_TOKEN);
      }
    }
  }

  @Transactional
  public void saveSsgAuthToken(String tokenKey, OrderSiteType siteType) {
    ssgTokenRepository.save(SsgTokenEntity.builder()
        .tokenKey(tokenKey)
        .siteType(siteType.getShortCode())
        .expireDate(now().plus(Duration.ofDays(1)))
        .build());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public long updateProcesserFail(final long orderNo,
      final String orderSiteType,
      final String tradeType,
      final String errorCode
  ) {
    return ssgPointTargetRepositorySupport.updatePrcoesserFailBy(orderNo , orderSiteType , tradeType , errorCode);
  }

  @Transactional
  public long updateWriterSuceess(final SsgPointTargetDto item) {
    if(item.getResponseCode().equals("PRC4081") && item.getStatus() != PointStatusType.Ready) {
      return 1L;
    }
    return ssgPointTargetRepositorySupport.updatePointTarget(item.getOrderNo() ,
        item.getBuyerId() ,
        item.getSiteType(),
        item.getTradeType(),
        item.getAccountDate(),
        DATE_TIME_STRING_FORMATTER.parse(item.getRequestDate() , Instant::from),
        item.getResponseCode(),
        item.getPntApprId(),
        item.getSaveAmount(),
        PointStatusType.Ready.getCode()
    );
  }

  @Transactional
  public long updateWriterRecoverSuceess(final SsgPointTargetDto item) {
    return ssgPointTargetRepositorySupport.updatePointTarget(item.getOrderNo(),
        item.getBuyerId(),
        item.getSiteType(),
        item.getTradeType(),
        item.getAccountDate(),
        DATE_TIME_STRING_FORMATTER.parse(item.getRequestDate(), Instant::from),
        item.getResponseCode(),
        item.getPntApprId(),
        item.getSaveAmount(),
        PointStatusType.Fail.getCode()
    );
  }
}
