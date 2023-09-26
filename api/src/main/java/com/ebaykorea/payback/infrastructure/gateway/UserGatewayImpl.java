package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.gateway.UserGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.member.QuiltApiClient;
import com.ebaykorea.payback.infrastructure.gateway.client.member.dto.QuiltBaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ebaykorea.payback.core.exception.PaybackExceptionCode.API_GATEWAY_002;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

  private final QuiltApiClient quiltApiClient;

  @Override
  public Optional<String> findUserKey(String buyerNo) {
    return quiltApiClient.findUserKey(buyerNo)
        .flatMap(QuiltBaseResponse::findSuccessData);
  }

  @Override
  public String getUserId(final String userToken) {
    return quiltApiClient.findUserId(userToken)
        .flatMap(QuiltBaseResponse::findSuccessData)
        .orElseThrow(() -> new PaybackException(API_GATEWAY_002, "userId 없음"));
  }
}
