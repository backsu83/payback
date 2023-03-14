//package com.ebaykorea.payback.core.domain.entity.ssgpoint;
//
//
//import static com.ebaykorea.payback.core.domain.constant.PointTradeType.Save;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.ebaykorea.payback.util.PaybackDateTimes;
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//@Disabled
//class SsgPointTest {
//
//  @Test
//  void ssgPointReceiptNoTest() {
//
//    SsgPointUnit gmksave = SsgPointUnit.builder()
//        .orderNo(13092892548L)
//        .pointStatus(SsgPointStatus.builder()
//            .pointTradeType(Save)
//            .build())
//        .build();
//
//    String receiptNo = gmksave.getReceiptNo("GMK");
//    String yyMMddHHmmss = LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_TIME_STRING_FORMATTER);
//
//    assertEquals(receiptNo , "GMK" + yyMMddHHmmss + "S" + "2548");
//    assertEquals(receiptNo.length() , 20);
//  }
//
//  @Test
//  void ssgPointTradeNoTest() {
//    SsgPointUnit gmksave = SsgPointUnit.builder()
//        .orderNo(13092892548L)
//        .pointStatus(SsgPointStatus.builder()
//            .pointTradeType(Save)
//            .build())
//        .build();
//
//    String tradeNo = gmksave.getTradeNo();
//    assertEquals(tradeNo , "S" + "30C65AF84");
//    assertEquals(tradeNo.length() , 10);
//  }
//
//  @Test
//  void ssgPointTransactionNoTest() {
//    SsgPointUnit gmksave = SsgPointUnit.builder()
//        .orderNo(13092892548L)
//        .pointStatus(SsgPointStatus.builder()
//            .pointTradeType(Save)
//            .build())
//        .build();
//
//    String transactionNo = gmksave.getTransactionNo();
//    String mmddhh = LocalDateTime.now().format(PaybackDateTimes.LOCAL_DATE_STRING_FORMATTER);
//
//    assertEquals(transactionNo , "S" + "13092892548" + mmddhh + "00");
//    assertEquals(transactionNo.length() , 20);
//  }
//}