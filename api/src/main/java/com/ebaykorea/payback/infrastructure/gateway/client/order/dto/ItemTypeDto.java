package com.ebaykorea.payback.infrastructure.gateway.client.order.dto;

import lombok.Data;

@Data
public class ItemTypeDto {
  /** 환금성여부 */
  Boolean isMoneyCategory;
}
