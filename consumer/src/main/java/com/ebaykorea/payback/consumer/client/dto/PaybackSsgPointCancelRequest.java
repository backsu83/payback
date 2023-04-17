package com.ebaykorea.payback.consumer.client.dto;

import com.ebaykorea.payback.consumer.event.OrderSiteType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaybackSsgPointCancelRequest {
  private OrderSiteType siteType;
  private Long packNo;
}
