package com.ebaykorea.payback.scheduler.client.dto.payback;

import com.ebaykorea.payback.scheduler.model.constant.OrderSiteType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelRequestDto {
    private OrderSiteType siteType;
    private Long packNo;
    private String buyerId;
}
