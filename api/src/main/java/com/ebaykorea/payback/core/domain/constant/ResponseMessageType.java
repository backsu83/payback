package com.ebaykorea.payback.core.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessageType {

  CASHBACK_CREATED("캐시백 생성"),
  CASHBACK_DUPLICATED("캐시백 중복건"),
  CASHBACK_INVALID_TARGET("캐시백 적립대상 아님"),

  SSGPOINT_CREATED("SSG 포인트 생성"),
  SSGPOINT_CANCELED("SSG 포인트 취소"),
  SSGPOINT_RETRIED("SSG 포인트 재처리"),
  SSGPOINT_DUPLICATED("SSG 포인트 중복건"),

  SUCCESS("처리 완료");

  private final String message;
}
