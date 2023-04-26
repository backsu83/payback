package com.ebaykorea.payback.batch.domain;

import com.ebaykorea.payback.batch.domain.constant.OrderSiteType;
import com.ebaykorea.payback.batch.domain.constant.PointTradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SsgPointVerifyDto {
    private String tradeDate;
    private OrderSiteType siteType;
    private PointTradeType tradeType;
    private Long count;
    private BigDecimal amount;
    private String returnCode;
    private String returnMessage;
    private String InsertOperator;
    private Instant InsertDate;
    private String UpdateOperator;
    private Instant UpdateDate;
}
