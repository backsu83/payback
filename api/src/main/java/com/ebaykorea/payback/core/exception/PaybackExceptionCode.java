package com.ebaykorea.payback.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaybackExceptionCode {
    /**
    네이밍: 일반적인 코드를 제외한 예외처리 경우 패키징명_넘버링
     */
    VALID_001(""),
    VALID_002(""),
    VALID_003(""),

    DOMAIN_ENTITY_001("불변성 오류: {0}"),

    GATEWAY_001("처리 실패 : {0}"),
    GATEWAY_002("API 조회 실패"),
    GATEWAY_003(""),

    PERSISTENCE_001(""),
    PERSISTENCE_002(""),
    PERSISTENCE_003("");

    private final String message;
}
