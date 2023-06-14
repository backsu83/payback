package com.ebaykorea.payback.batch.service;

import static com.ebaykorea.payback.batch.domain.exception.BatchProcessorExceptionCode.ERR_TOKEN;
import static com.ebaykorea.payback.batch.util.PaybackInstants.now;

import com.ebaykorea.payback.batch.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.domain.exception.BatchProcessorException;
import com.ebaykorea.payback.batch.repository.opayreward.SsgTokenRepository;
import com.ebaykorea.payback.batch.repository.opayreward.entity.SsgTokenEntity;
import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SsgPointTokenService {

    private final SsgTokenRepository ssgTokenRepository;
    private final SsgPointApiClient ssgPointApiClient;

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
            throw new BatchProcessorException(ERR_TOKEN);
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
