package com.ebaykorea.payback.consumer.event;

import java.util.List;
import lombok.Data;

@Data
public class OrderCanceledGmarketEvent implements OrderCanceledEvent {

  private String refundBundleKey;
  private String regId;
  private Long packNo;
  private List<Long> contrNoList;

  @Override
  public Long getPayNo() {
    return packNo;
  }

  @Override
  public List<Long> getOrderNos() {
    return contrNoList;
  }
}
