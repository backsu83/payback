package com.ebaykorea.payback.infrastructure.gateway;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.AUCTION_TENANT;
import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.API_GATEWAY_004;
import static com.ebaykorea.payback.util.support.MDCDecorator.withMdc;

import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.member.QuiltApiClient;
import com.ebaykorea.payback.infrastructure.gateway.client.member.dto.QuiltBaseResponse;
import com.ebaykorea.payback.util.PaybackStrings;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(AUCTION_TENANT)
@Service
@RequiredArgsConstructor
public class AuctionUserGateway implements UserGateway {

  private final QuiltApiClient quiltApiClient;

  @Override
  public CompletableFuture<String> findSmileUserKeyAsync(final String memberId) {
    return CompletableFuture.supplyAsync(withMdc(() -> quiltApiClient.findSmileUserKeyByMemberId(memberId)))
        .thenApply(result -> result
            .flatMap(QuiltBaseResponse::findSuccessData)
            .orElse(PaybackStrings.EMPTY));
  }

  @Override
  public String getUserId(final String userToken) {
    return quiltApiClient.findUserId(userToken)
        .flatMap(QuiltBaseResponse::findSuccessData)
        .orElseThrow(() -> new PaybackException(API_GATEWAY_004, userToken));
  }
}
