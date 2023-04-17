package com.ebaykorea.payback.core.dto.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsgPointTarget {
  private Long packNo;
  private Long orderNo;
  private String buyerId;
  private String pointStatus;
  private String tradeType;
  private String receiptNo;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private Instant orderDate;
  private String scheduleDate;
  private String accountDate;
  private String pntApprId;
  private String adminId;

  public boolean isCancelType() {
    return Objects.equals(tradeType, PointTradeType.Cancel.getCode());
  }
  public boolean isSaveType() {
    return Objects.equals(tradeType, PointTradeType.Save.getCode());
  }
}
