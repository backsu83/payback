package com.ebaykorea.payback.batch.service;

import com.ebaykorea.payback.batch.config.client.smileclub.SmileClubApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.SsgPointApiClient;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SsgPointProcesserService {

  private final SsgPointApiClient ssgPointApiClient;
  private final SmileClubApiClient smileClubApiClient;
  public void earn() {
    final var cardNo = smileClubApiClient.getCardNo("partnerId" , "S001");
    //카드번호 : 복호화(지마켓) -> 암호화(AES256)
    final var tokenInfo = ssgPointApiClient.getAuthToken(SsgPointAuthTokenRequest.builder().build());


  }

  public void cancel() {

  }

}
