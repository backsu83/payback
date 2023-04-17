package com.ebaykorea.payback.batch.client.ssgpoint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsgPointAuthTokenRequest {
  private String clientId;
  private final String apiKey;
}
