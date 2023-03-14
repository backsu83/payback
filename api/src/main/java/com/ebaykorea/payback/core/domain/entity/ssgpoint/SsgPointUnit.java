package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.util.PaybackDateTimes;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SsgPointUnit {

  private Long orderNo;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private String scheduleDate;
  private SsgPointPayMethod ssgPointPayMethod;
  private Boolean isPolicy;
  private SsgPointStatus pointStatus;

  private SsgPointUnit(final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final String scheduleDate,
      final SsgPointPayMethod ssgPointPayMethod,
      final Boolean isPolicy,
      final SsgPointStatus pointStatus
  ) {
    this.orderNo = orderNo;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.scheduleDate = scheduleDate;
    this.ssgPointPayMethod = ssgPointPayMethod;
    this.isPolicy = isPolicy;
    this.pointStatus = pointStatus;
  }

  public static SsgPointUnit of(final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final String scheduleDate,
      final SsgPointPayMethod ssgPointPayMethod,
      final Boolean isPolicy,
      final SsgPointStatus pointState
  ) {
    return new SsgPointUnit(orderNo, payAmount, saveAmount, scheduleDate, ssgPointPayMethod, isPolicy, pointState);
  }

  //"AAA" + "YYMMDDHH24MISS" + S or C + 주문번호 마지막 4자리
  public String getReceiptNo(final String ticker) {
    return new StringBuilder()
        .append(ticker)
        .append(LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_TIME_STRING_FORMATTER))
        .append(pointStatus.getPointTradeType().getCode())
        .append(String.valueOf(orderNo).substring(String.valueOf(orderNo).length()-4))
        .toString();
  }

  //S or C +주문번호(16진수) + padding
  public String getTradeNo() {
    String tradeNo = new StringBuilder()
        .append(pointStatus.getPointTradeType().getCode())
        .append(Long.toHexString(Long.valueOf(orderNo)))
        .toString()
        .toUpperCase();
    return Strings.padEnd(tradeNo,10 , '0');
  }

  //S or C +주문번호 + MMDDHH + padding
  public String getTransactionNo() {
    String transactionNo = new StringBuilder()
        .append(pointStatus.getPointTradeType().getCode())
        .append(orderNo)
        .append(LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_STRING_FORMATTER))
        .toString();
    return Strings.padEnd(transactionNo,20 , '0');
  }
}
