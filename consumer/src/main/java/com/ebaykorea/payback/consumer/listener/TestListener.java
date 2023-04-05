//package com.ebaykorea.payback.consumer.listener;
//
//import com.ebaykorea.payback.consumer.event.OrderCanceledAuctionEvent;
//import javax.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class TestListener {
//
//  @KafkaListener(
//      topics = {"hello.kafka2"}
//  )
//  public void consumeForSsgPoints(@Payload @Valid final OrderCanceledAuctionEvent value) {
//    System.out.println("payback-consumer is getPackNo: " + value.getPackNo());
//    System.out.println("payback-consumer is getOrderNo: " + value.getOrderNo());
//    //ssgpoint
//  }
//
//}
