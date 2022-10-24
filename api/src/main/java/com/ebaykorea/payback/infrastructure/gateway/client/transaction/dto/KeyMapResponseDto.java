package com.ebaykorea.payback.infrastructure.gateway.client.transaction.dto;

import lombok.Data;

import java.util.List;

@Data
public class KeyMapResponseDto {
  private List<KeyMapDto> orders;
}
