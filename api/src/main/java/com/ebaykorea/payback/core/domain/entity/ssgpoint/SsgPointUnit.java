package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointTradeType;
import com.ebaykorea.payback.core.domain.entity.ssgpoint.state.SsgPointState;
import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Value;

import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.DATE_TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackDateTimeFormatters.TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;

@Value
@Builder
public class SsgPointUnit {

  Long orderNo;
  BigDecimal payAmount;
  BigDecimal saveAmount;
  Instant scheduleDate;
  String accountDate;
  String pointToken;
  Boolean isPolicy;
  SsgPointStatus pointStatus;
  SsgPointOrigin pointOrigin;

  String adminId;

  public static SsgPointUnit EMPTY = SsgPointUnit.of(0L, BigDecimal.ZERO, BigDecimal.ZERO, now(), null, null,false, null, null, null);

  private SsgPointUnit(final Long orderNo,
                       final BigDecimal payAmount,
                       final BigDecimal saveAmount,
                       final Instant scheduleDate,
                       final String accountDate,
                       final String pointToken,
                       final Boolean isPolicy,
                       final SsgPointStatus pointStatus,
                       final SsgPointOrigin pointOrigin,
                       final String adminId) {
    this.orderNo = orderNo;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.scheduleDate = scheduleDate;
    this.accountDate = accountDate;
    this.pointToken = pointToken;
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
                                final String pointToken,
                                final Boolean isPolicy,
                                final SsgPointStatus pointState,
                                final SsgPointOrigin pointOrigin,
                                final String adminId
  ) {
    return new SsgPointUnit(orderNo, payAmount, saveAmount, scheduleDate, accountDate, pointToken, isPolicy, pointState,
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
    return of(orderNo, payAmount, saveAmount, scheduleDate, null, null, isPolicy, state.ready(), pointOrigin, adminId);
  }

  public static SsgPointUnit cancelUnit(
      final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final String accountDate,
      final String pointToken,
      final Boolean isPolicy,
      final SsgPointState state,
      final SsgPointOrigin pointOrigin,
      final String adminId
  ) {
    return of(orderNo, payAmount, saveAmount, scheduleDate, accountDate, pointToken, isPolicy, state.cancel(), pointOrigin, adminId);
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
    return of(orderNo, payAmount, saveAmount, scheduleDate, null, null, isPolicy, state.withhold(), pointOrigin, adminId);
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

  //적립(10 + 주문번호) / 취소(20 + 주문번호)
  public String getTradeNo() {
    var preFix = PointTradeType.from(pointStatus.getTradeType().getCode()).getNumberCode();
    final var tradeNo = String.format("%s%s", preFix, orderNo);
    return Strings.padEnd(tradeNo,10,'0').substring(0, 10);
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
