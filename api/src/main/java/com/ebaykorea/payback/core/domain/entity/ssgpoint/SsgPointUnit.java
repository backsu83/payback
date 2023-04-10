package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Value;

import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackDateTimes.TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;

@Value
@Builder
public class SsgPointUnit {

  Long orderNo;
  BigDecimal payAmount;
  BigDecimal saveAmount;
  Instant scheduleDate;
  Boolean isPolicy;
  SsgPointStatus pointStatus;
  SsgPointOrigin pointOrigin;

  String adminId;

  public static SsgPointUnit EMPTY = SsgPointUnit.of(0L, BigDecimal.ZERO, BigDecimal.ZERO, now(), false, null, null, null);

  private SsgPointUnit(final Long orderNo,
                       final BigDecimal payAmount,
                       final BigDecimal saveAmount,
                       final Instant scheduleDate,
                       final Boolean isPolicy,
                       final SsgPointStatus pointStatus,
                       final SsgPointOrigin pointOrigin,
                       final String adminId) {
    this.orderNo = orderNo;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.scheduleDate = scheduleDate;
    this.isPolicy = isPolicy;
    this.pointStatus = pointStatus;
    this.pointOrigin = pointOrigin;
    this.adminId = adminId;
  }

  public static SsgPointUnit of(final Long orderNo,
                                final BigDecimal payAmount,
                                final BigDecimal saveAmount,
                                final Instant scheduleDate,
                                final Boolean isPolicy,
                                final SsgPointStatus pointState,
                                final SsgPointOrigin pointOrigin,
                                final String adminId
  ) {
    return new SsgPointUnit(orderNo, payAmount, saveAmount, scheduleDate, isPolicy, pointState,
        pointOrigin, adminId);
  }

  //"AAA" + "YYMMDDHH24MISS" + S or C + 주문번호 마지막 4자리
  public String getReceiptNo(final String ticker, final Instant orderDate) {
    return new StringBuilder()
        .append(ticker)
        .append(DATE_TIME_STRING_FORMATTER.format(orderDate))
        .append(pointStatus.getTradeType().getCode())
        .append(String.valueOf(orderNo).substring(String.valueOf(orderNo).length() - 4))
        .toString();
  }

  //S or C +주문번호(16진수) + padding
  public String getTradeNo() {
    String tradeNo = new StringBuilder()
        .append(pointStatus.getTradeType().getCode())
        .append(Long.toHexString(orderNo))
        .toString()
        .toUpperCase();
    return Strings.padEnd(tradeNo, 10, '0');
  }

  //S or C +주문번호 + MMDDHH + padding
  public String getTransactionNo() {
    String transactionNo = new StringBuilder()
        .append(pointStatus.getTradeType().getCode())
        .append(orderNo)
        .append(TIME_STRING_FORMATTER.format(Instant.now()))
        .toString();
    return Strings.padEnd(transactionNo, 20, '0');
  }
}
