package com.ebaykorea.payback.batch.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaybackExceptionCode {

  DOMAIN_ENTITY_001("불변식 오류: {0}"),
  DOMAIN_ENTITY_002("{0}를 찾을 수 없습니다"),
  DOMAIN_ENTITY_004("{0} 값이 없습니다"),
  DOMAIN_ENTITY_008("{0}는 null일 수 없습니다"),
  DOMAIN_ENTITY_010("{0} 정보가 없습니다"),

  DOMAIN_SSG_ENTITY_001("포인트 정책 조회 실패"),
  DOMAIN_SSG_ENTITY_002("주문 금액 조회 실패"),
  DOMAIN_SSG_ENTITY_003("사이트 구분 조회실패"),

  DOMAIN_SSG_ENTITY_004("SSG 포인트 적립 실패 : {0}"),
  DOMAIN_SSG_ENTITY_005("카드번호 암복화 실패 : {0}"),

  API_GATEWAY_001("처리 실패 : {0}"),
  API_GATEWAY_002("API 조회 : {0}"),
  API_GATEWAY_003("카드 번호 조회 실패 : {0}");

  private final String message;
}
