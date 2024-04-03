package com.ebaykorea.payback.schedulercluster.client.dto.smilecash;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class MassSaveRequestDto {
  private String shopTransactionId;
  private String shopId;
  private String subShopId;
  private String shopOrderId;
  private String promotionId;
  private SmileCashAuthDto smileCash;
}
