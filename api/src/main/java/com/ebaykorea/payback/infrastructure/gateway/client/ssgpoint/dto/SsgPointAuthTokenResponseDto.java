package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Data;

@Data
public class SsgPointAuthTokenResponseDto {
  private String clientId;
  private String apiKey;
  private String responseCd;
  private String responseMsg;
  private String tokenId;
  private String avlbDt;
  private String avlbTs;
}
