package com.ebaykorea.payback.batch.domain.constant;

import com.ebaykorea.payback.batch.util.PaybackEnums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public enum VerifyTradeType {
    Save("000001", "S"),
    Cancel("000002", "C");

    private final String code;
    private final String shortCode;

    private static transient Map<String, VerifyTradeType> map = PaybackEnums.reverseMap(VerifyTradeType.class, VerifyTradeType::getShortCode);

    @JsonCreator
    public static VerifyTradeType forValue(String value) {
        return map.getOrDefault(value, Save);
    }

    @JsonValue
    public String toValue() {
        return this.shortCode;
    }
}
