package com.ebaykorea.payback.batch.config.client.ssgpoint.dto;

import lombok.Data;

@Data
public class SsgPointAuthTokenResponse {
  private String clientId;
  private String apiKey;
  private String responseCd;
  private String responseMsg;
  private String tokenId;
  private String avlbDt;
  private String avlbTs;
}
