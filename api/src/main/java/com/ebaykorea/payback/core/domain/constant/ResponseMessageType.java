package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageType {

  CASHBACK_CREATED("캐시백 생성"),
  CASHBACK_DUPLICATED("캐시백 중복건"),
  CASHBACK_INVALID_TARGET("캐쉬백 적립대상 아님"),

  SSGPOINT_CREATED("SSG 포인트 생성"),
  SSGPOINT_CANCELED("SSG 포인트 취소");

  private final String message;
}
