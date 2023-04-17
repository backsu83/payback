package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
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
  String accountDate;
  Boolean isPolicy;
  SsgPointStatus pointStatus;
  SsgPointOrigin pointOrigin;

  String adminId;

  public static SsgPointUnit EMPTY = SsgPointUnit.of(0L, BigDecimal.ZERO, BigDecimal.ZERO, now(), null,false, null, null, null);

  private SsgPointUnit(final Long orderNo,
                       final BigDecimal payAmount,
                       final BigDecimal saveAmount,
                       final Instant scheduleDate,
                       final String accountDate,
                       final Boolean isPolicy,
                       final SsgPointStatus pointStatus,
                       final SsgPointOrigin pointOrigin,
                       final String adminId) {
    this.orderNo = orderNo;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.scheduleDate = scheduleDate;
    this.accountDate = accountDate;
    this.isPolicy = isPolicy;
    this.pointStatus = pointStatus;
    this.pointOrigin = pointOrigin;
    this.adminId = adminId;
  }

  private static SsgPointUnit of(final Long orderNo,
                                final BigDecimal payAmount,
                                final BigDecimal saveAmount,
                                final Instant scheduleDate,
                                final String accountDate,
                                final Boolean isPolicy,
                                final SsgPointStatus pointState,
                                final SsgPointOrigin pointOrigin,
                                final String adminId
  ) {
    return new SsgPointUnit(orderNo, payAmount, saveAmount, scheduleDate, accountDate, isPolicy, pointState,
        pointOrigin, adminId);
  }

  public static SsgPointUnit readyUnit(
      final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final Boolean isPolicy,
      final SsgPointState state,
      final SsgPointOrigin pointOrigin,
      final String adminId
  ) {
    return of(orderNo, payAmount, saveAmount, scheduleDate, null, isPolicy, state.ready(), pointOrigin, adminId);
  }

  public static SsgPointUnit cancelUnit(
      final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final String accountDate,
      final Boolean isPolicy,
      final SsgPointState state,
      final SsgPointOrigin pointOrigin,
      final String adminId
  ) {
    return of(orderNo, payAmount, saveAmount, scheduleDate, accountDate, isPolicy, state.cancel(), pointOrigin, adminId);
  }

  public static SsgPointUnit withholdUnit(
      final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final Boolean isPolicy,
      final SsgPointState state,
      final SsgPointOrigin pointOrigin,
      final String adminId
  ) {
    return of(orderNo, payAmount, saveAmount, scheduleDate, null, isPolicy, state.withhold(), pointOrigin, adminId);
  }

  //"AAA" + "YYMMDDHH24MISS" + S or C + 주문번호 마지막 4자리
  public String getReceiptNo(final String ticker, final Instant orderDate) {
    return String.format("%s%s%s%s",
        ticker,
        DATE_TIME_STRING_FORMATTER.format(orderDate),
        pointStatus.getTradeType().getCode(),
        String.valueOf(orderNo).substring(orderNoLength() - (Math.min(orderNoLength(), 4))));
  }

  private int orderNoLength() {
    return String.valueOf(orderNo).length();
  }

  //S or C +주문번호(16진수) + padding
  public String getTradeNo() {
    if( pointStatus.getTradeType() == PointTradeType.Save) {
      return String.format("%s%s", "10", orderNo).substring(0,10);
    } else {
      return String.format("%s%s", "20", orderNo).substring(0,10);
    }
  }

  //S or C +주문번호 + MMDDHH + padding
  public String getTransactionNo() {
    String transactionNo = String.format("%s%d%s",
        pointStatus.getTradeType().getCode(),
        orderNo,
        TIME_STRING_FORMATTER.format(Instant.now()));
    return Strings.padEnd(transactionNo, 20, '0');
  }
}
