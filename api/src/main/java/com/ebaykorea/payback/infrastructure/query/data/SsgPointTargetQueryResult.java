package com.ebaykorea.payback.infrastructure.query.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;

@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsgPointTargetQueryResult {
    private BigDecimal ssgPointSaveAmount;
    private String ssgPointSaveExpectDate;
    private String ssgPointSavedYN;
    private String ssgPointTradeType;
}