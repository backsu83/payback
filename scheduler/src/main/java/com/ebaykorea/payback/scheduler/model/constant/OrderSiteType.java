package com.ebaykorea.payback.scheduler.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderSiteType {
    Unknown(null, "" , ""),
    Gmarket(0, "G" , "GMK"),
    Auction(1, "A" , "AUC");

    private final Integer code;
    private final String shortCode;
    private final String ticker;
}
