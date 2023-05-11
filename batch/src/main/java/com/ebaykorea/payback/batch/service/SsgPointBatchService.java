package com.ebaykorea.payback.batch.service;

import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_CARD_CRYPTO;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADD;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_PNTADDCNCL;
import static com.ebaykorea.payback.batch.domain.exception.BatchProcesserExceptionCode.ERR_TOKEN;
import static com.ebaykorea.payback.batch.util.PaybackInstants.now;

import com.ebaykorea.payback.batch.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.batch.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointVerifyRequest;
import com.ebaykorea.payback.batch.domain.SsgPointBatchUnit;
import com.ebaykorea.payback.batch.domain.SsgPointCertifier;
import com.ebaykorea.payback.batch.domain.SsgPointProcesserDto;
import com.ebaykorea.payback.batch.domain.SsgPointTargetDto;
import com.ebaykorea.payback.batch.domain.SsgPointVerifyDto;
import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.VerifyTradeType;
import com.ebaykorea.payback.batch.domain.exception.BatchProcesserException;
import com.ebaykorea.payback.batch.job.mapper.SsgPointCancelProcesserMapper;
import com.ebaykorea.payback.batch.job.mapper.SsgPointEarnProcesserMapper;
import com.ebaykorea.payback.batch.job.mapper.SsgPointVerifyProcesserMapper;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointDailyVerifyRepository;
import com.ebaykorea.payback.batch.repository.opayreward.SsgPointTargetRepositorySupport;
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgPointDailyVerifyEntity;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import com.ebaykorea.payback.batch.util.support.CryptoAES256;
import com.ebaykorea.payback.batch.util.support.CryptoArche;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointBatchService {
  private final SsgPointApiClient ssgPointApiClient;
  private final SmileClubApiClient smileClubApiClient;
  private final SsgTokenRepository ssgTokenRepository;

  private final SsgPointDailyVerifyRepository ssgPointDailyVerifyRepository;
  private final SsgPointEarnProcesserMapper ssgPointEarnProcesserMapper;
  private final SsgPointCancelProcesserMapper ssgPointCancelProcesserMapper;

  private final SsgPointVerifyProcesserMapper ssgPointVerifyProcesserMapper;
  private final SsgPointTargetRepositorySupport ssgPointTargetRepositorySupport;



  public SsgPointTargetDto earn(final SsgPointProcesserDto item, SsgPointCertifier certifier) {
    final var cardNo = getCardNo(item.getBuyerId(), item.getSiteType(), certifier);
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey(), item.getSiteType().getShortCode());
    try {
      var request = ssgPointEarnProcesserMapper.mapToRequest(item, certifier, tokenId, cardNo);
      final var response = ssgPointApiClient.earnPoint(request);
      return ssgPointEarnProcesserMapper.mapToTarget(request.getBusiDt(), request.getRequestDate(), cardNo, response, item);
    } catch (Exception ex) {
      log.error("orderNo:[{}] errorMessage:[{}]", item.getOrderNo(), ex.getLocalizedMessage());
      throw new BatchProcesserException(ERR_PNTADD);
    }
  }

  public SsgPointTargetDto cancel(final SsgPointProcesserDto item, SsgPointCertifier certifier) {
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey(), item.getSiteType().getShortCode());
    try {
      var request = ssgPointCancelProcesserMapper.mapToRequest(item, certifier, tokenId);
      final var response = ssgPointApiClient.cancelPoint(request);
      return ssgPointCancelProcesserMapper.mapToTarget(request.getBusiDt(), request.getRequestDate(), response, item);
    } catch (Exception ex) {
      log.error("orderNo:[{}] errorMessage:[{}]", item.getOrderNo(), ex.getLocalizedMessage());
      throw new BatchProcesserException(ERR_PNTADDCNCL);
    }
  }

  public SsgPointVerifyDto verify(SsgPointCertifier certifier, OrderSiteType orderSiteType, VerifyTradeType verifyTradeType) {
    final var tokenId = getSsgAuthToken(certifier.getClientId(), certifier.getApiKey(), orderSiteType.getShortCode());
    final var sumEntity = ssgPointTargetRepositorySupport.findSumCount(orderSiteType, verifyTradeType);
    final var unit = new SsgPointBatchUnit();
    SsgPointVerifyRequest request = SsgPointVerifyRequest.builder()
            .clientId(certifier.getClientId())
            .apiKey(certifier.getApiKey())
            .tokenId(tokenId)
            .reqTrcNo(unit.getVerifyTransactionNo())
            .reqDate(unit.getRequestDate())
            .sumCount(sumEntity.getSumCount())
            .sumAmt(sumEntity.getSumAmount())
            .tradeType(verifyTradeType.getCode())
            .brchId(certifier.getBranchId())
            .build();
    var response = ssgPointApiClient.verifyPoint(request);
    return ssgPointVerifyProcesserMapper.mapToVerify(request, response, orderSiteType, verifyTradeType);
  }

  @Transactional
  public void saveVerifySuceess(final SsgPointVerifyDto verify) {
    SsgPointDailyVerifyEntity entity = SsgPointDailyVerifyEntity.builder()
            .tradeDate(verify.getTradeDate())
            .siteType(verify.getSiteType().getShortCode())
            .tradeType(verify.getTradeType().getCode())
            .count(verify.getCount())
            .amount(verify.getAmount())
            .returnCode(verify.getReturnCode())
            .returnMessage(verify.getReturnMessage())
            .insertDate(Instant.now())
            .insertOperator("verifyBatch")
            .updateDate(Instant.now())
            .updateOperator("verifyBatch")
            .build();
    ssgPointDailyVerifyRepository.save(entity);

  }

  public String getCardNo(final String buyerId, OrderSiteType siteType, SsgPointCertifier auth) {
    try {
      final var cardNo = smileClubApiClient.getCardNo(auth.getMemberKey(), buyerId).getCardNo();
      log.info("smileclub carNo : [{}][{}][{}]", siteType, buyerId, cardNo);
      final var decryptCardNo= CryptoArche.decrypt(cardNo, auth.getDecryptInstance());
      final var encryptCardNo = CryptoAES256.encrypt(decryptCardNo, auth.getEncryptKey(), auth.getEncryptIv());
      return encryptCardNo;
    } catch (Exception ex) {
      log.error(ex.getLocalizedMessage());
      throw new BatchProcesserException(ERR_CARD_CRYPTO);
    }
  }

  /**
   * ssgpoint tokenid 조회
   * 최대 1일 사용가능
   * @return
   */
  @Cacheable(cacheNames = "SSG_TOKEN_KEY", key = "#siteType")
  public String getSsgAuthToken(String clientId , String apiKey, String siteType) {
    log.debug("getSsgAuthToken: [{}][{}][{}]", clientId , apiKey, siteType);
    SsgTokenEntity entity = ssgTokenRepository.findTopBySiteTypeAndExpireDateAfterOrderByExpireDateDesc(siteType ,now());
    if(Objects.nonNull(entity)) {
      return entity.getTokenKey();
    }

    try {
      final var request = SsgPointAuthTokenRequest.builder()
          .clientId(clientId)
          .apiKey(apiKey)
          .build();
      final var tokenInfo = ssgPointApiClient.getAuthToken(request);
      saveSsgAuthToken(tokenInfo.getTokenId() , siteType);
      return tokenInfo.getTokenId();
    } catch (Exception ex) {
      log.error(ex.getLocalizedMessage());
      throw new BatchProcesserException(ERR_TOKEN);
    }
  }

  @Transactional
  public void saveSsgAuthToken(String tokenKey, String siteType) {
    log.debug("saveSsgAuthToken: [{}][{}]", tokenKey , siteType);
    ssgTokenRepository.save(SsgTokenEntity.builder()
        .tokenKey(tokenKey)
        .siteType(siteType)
        .expireDate(now().plus(Duration.ofDays(1)))
        .build());
  }
}
