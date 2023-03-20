package com.ebaykorea.payback.batch.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CellPhoneAuthType {
    Unknown(""),
    Mobilians("M1"),
    LGUPlus("M3"),
    Galaxia("");

    private final String code;
}