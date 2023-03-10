package com.ebaykorea.payback.infrastructure.gateway;

import com.ebaykorea.payback.core.domain.entity.ssgpoint.SsgPointCard;
import com.ebaykorea.payback.core.exception.PaybackException;
import com.ebaykorea.payback.core.exception.PaybackExceptionCode;
import com.ebaykorea.payback.core.gateway.SmileClubGateway;
import com.ebaykorea.payback.infrastructure.gateway.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.infrastructure.gateway.mapper.SmileClubGatewayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmileClubGatewayImpl implements SmileClubGateway {

  private final SmileClubApiClient smileClubApiClient;
  private final SmileClubGatewayMapper smileClubGatewayMapper;

  @Override
  public SsgPointCard getCardNo(String partnerId, String partnerMemberKey) {
    return smileClubGatewayMapper.map(smileClubApiClient.getCardNo(partnerId , partnerMemberKey).orElseThrow(
        ()-> new PaybackException(PaybackExceptionCode.API_GATEWAY_003, partnerId+ "/"+partnerMemberKey)
    ));
  }
}
