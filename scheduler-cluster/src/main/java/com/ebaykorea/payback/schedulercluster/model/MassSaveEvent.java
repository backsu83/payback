package com.ebaykorea.payback.schedulercluster.model;

import com.ebaykorea.payback.schedulercluster.client.dto.smilecash.SmileCashAuthDto;
import lombok.Data;


@Data
public class MassSaveEvent {

  private String memberKey;
  private String operator;
  private int retryCount;
  private long seqNo;
  private int status;
  private String shopTransactionId;
  private String shopId;
  private String subShopId;
  private String shopOrderId;
  private String promotionId;
  private SmileCashAuthDto smileCash;
}
