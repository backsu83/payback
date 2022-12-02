package com.ebaykorea.payback.api.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageType {

  CASHBACK_DUPLICATIED("캐쉬백 중복건"),
  CASHBACK_INVALID_TARGET("캐쉬백 적립대상 아님");

  private final String message;
}
