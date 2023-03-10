package com.ebaykorea.payback.core.domain.entity.ssgpoint;

import com.ebaykorea.payback.core.domain.constant.PointStatusType;
import com.ebaykorea.payback.core.domain.constant.PointTradeType;
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
  private PointStatusType pointStatus;
  private PointTradeType pointTradeType;
  private BigDecimal payAmount;
  private BigDecimal saveAmount;
  private String pointToken;
  private String scheduleDate;
  private SsgPointPayMethod ssgPointPayMethod;
  private Boolean isPolicy;

  private SsgPointUnit(final Long orderNo,
      final PointStatusType pointStatus,
      final PointTradeType pointTradeType,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final String pointToken,
      final String scheduleDate,
      final SsgPointPayMethod ssgPointPayMethod,
      final Boolean isPolicy
  ) {
    this.orderNo = orderNo;
    this.pointStatus = pointStatus;
    this.pointTradeType = pointTradeType;
    this.payAmount = payAmount;
    this.saveAmount = saveAmount;
    this.pointToken = pointToken;
    this.scheduleDate = scheduleDate;
    this.ssgPointPayMethod = ssgPointPayMethod;
    this.isPolicy = isPolicy;
  }

  public static SsgPointUnit of(final Long orderNo,
      final PointStatusType pointStatus,
      final PointTradeType pointTradeType,
      final BigDecimal payAmount,
      final BigDecimal saveAmount,
      final String pointToken,
      final String scheduleDate,
      final SsgPointPayMethod ssgPointPayMethod,
      final Boolean isPolicy
  ) {
    return new SsgPointUnit(orderNo, pointStatus, pointTradeType, payAmount, saveAmount, pointToken, scheduleDate,
        ssgPointPayMethod, isPolicy);
  }

  //"AAA" + "YYMMDDHH24MISS" + S or C + 주문번호 마지막 4자리
  public String getReceiptNo(final String ticker) {
    return new StringBuilder()
        .append(ticker)
        .append(LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_TIME_STRING_FORMATTER))
        .append(pointTradeType.getCode())
        .append(String.valueOf(orderNo).substring(String.valueOf(orderNo).length()-4))
        .toString();
  }

  //S or C +주문번호(16진수) + padding
  public String getTradeNo() {
    String tradeNo = new StringBuilder()
        .append(pointTradeType.getCode())
        .append(Long.toHexString(Long.valueOf(orderNo)))
        .toString()
        .toUpperCase();
    return Strings.padEnd(tradeNo,10 , '0');
  }

  //S or C +주문번호 + MMDDHH + padding
  public String getTransactionNo() {
    String transactionNo = new StringBuilder()
        .append(pointTradeType.getCode())
        .append(orderNo)
        .append(LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_STRING_FORMATTER))
        .toString();
    return Strings.padEnd(transactionNo,20 , '0');
  }
}
