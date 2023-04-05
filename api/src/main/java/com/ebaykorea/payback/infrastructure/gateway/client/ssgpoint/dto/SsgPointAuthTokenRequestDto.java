package com.ebaykorea.payback.infrastructure.gateway.client.ssgpoint.dto;

import lombok.Data;

@Data
public class SsgPointAuthTokenRequestDto {
  private String clientId;
  private final String apiKey;
}
