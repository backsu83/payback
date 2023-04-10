package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsgPointAuthTokenRequestDto {
  private String clientId;
  private String apiKey;
}
