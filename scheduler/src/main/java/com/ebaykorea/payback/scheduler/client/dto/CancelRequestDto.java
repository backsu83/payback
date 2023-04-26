package com.ebaykorea.payback.scheduler.client.dto;

import com.ebaykorea.payback.scheduler.domain.constant.OrderSiteType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelRequestDto {
    private OrderSiteType siteType;
    private Long packNo;
    private String buyerId;
}
