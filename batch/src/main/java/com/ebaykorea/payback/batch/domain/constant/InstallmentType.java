package com.ebaykorea.payback.batch.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstallmentType {
    Default("N"),
    InterestOnGmkt("C"),
    InterestFree("F");

    private final String code;


}
