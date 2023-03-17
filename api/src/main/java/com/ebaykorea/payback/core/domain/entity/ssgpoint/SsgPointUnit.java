package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.util.PaybackDateTimes;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
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
