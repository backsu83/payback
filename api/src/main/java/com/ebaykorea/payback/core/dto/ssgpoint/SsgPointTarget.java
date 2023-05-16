package com.ebaykorea.payback.core.dto.ssgpoint;

import static com.ebaykorea.payback.core.domain.constant.PointStatusType.CacnelReady;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.Cancel;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.Success;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.Unknown;
import static com.ebaykorea.payback.core.domain.constant.PointStatusType.WithHold;
import static com.ebaykorea.payback.util.PaybackInstants.now;

import com.ebaykorea.payback.core.domain.constant.OrderSiteType;
import com.ebaykorea.payback.core.domain.constant.PointStatusType;
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
  private OrderSiteType siteType;
  private String pointStatus;
  private String tradeType;
  private String receiptNo;
  private String pointToken;
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
