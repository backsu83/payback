package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import static com.ebaykorea.payback.util.PaybackDateTimes.DATE_TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackDateTimes.TIME_STRING_FORMATTER;
import static com.ebaykorea.payback.util.PaybackInstants.now;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Value;

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

  public static SsgPointUnit EMPTY = SsgPointUnit.of(0L, BigDecimal.ZERO, BigDecimal.ZERO, now(), false, null, null);

  private SsgPointUnit(final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final Boolean isPolicy,
      final SsgPointStatus pointStatus,
      final SsgPointOrigin pointOrigin) {
    this.orderNo = orderNo;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.scheduleDate = scheduleDate;
    this.isPolicy = isPolicy;
    this.pointStatus = pointStatus;
    this.pointOrigin = pointOrigin;
  }

  public static SsgPointUnit of(final Long orderNo,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final Instant scheduleDate,
      final Boolean isPolicy,
      final SsgPointStatus pointState,
      final SsgPointOrigin pointOrigin
  ) {
    return new SsgPointUnit(orderNo, payAmount, saveAmount, scheduleDate, isPolicy, pointState,
        pointOrigin);
  }

  //"AAA" + "YYMMDDHH24MISS" + S or C + 주문번호 마지막 4자리
  public String getReceiptNo(final String ticker) {
    return new StringBuilder()
        .append(ticker)
        .append(DATE_TIME_STRING_FORMATTER.format(Instant.now()))
        .append(pointStatus.getTradeType().getCode())
        .append(String.valueOf(orderNo).substring(String.valueOf(orderNo).length()-4))
        .toString();
  }

  //S or C +주문번호(16진수) + padding
  public String getTradeNo() {
    String tradeNo = new StringBuilder()
        .append(pointStatus.getTradeType().getCode())
        .append(Long.toHexString(Long.valueOf(orderNo)))
        .toString()
        .toUpperCase();
    return Strings.padEnd(tradeNo,10 , '0');
  }

  //S or C +주문번호 + MMDDHH + padding
  public String getTransactionNo() {
    String transactionNo = new StringBuilder()
        .append(pointStatus.getTradeType().getCode())
        .append(orderNo)
        .append(TIME_STRING_FORMATTER.format(Instant.now()))
        .toString();
    return Strings.padEnd(transactionNo,20 , '0');
  }
}
