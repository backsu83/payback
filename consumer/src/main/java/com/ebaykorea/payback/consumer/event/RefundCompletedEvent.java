package com.ebaykorea.payback.consumer.event;

import java.util.List;
import lombok.Data;

@Data
public class RefundCompletedEvent {

  private String refundBundleKey;
  private String regId;
  private Long packNo;
  private List<Long> contrNoList;
}
