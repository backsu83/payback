//package com.ebaykorea.payback.consumer.listener;
//
//import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;
//import com.ebaykorea.payback.consumer.event.RefundCompletedEvent;
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
//  public void consumeForSsgPoints(@Payload @Valid final OrderCreatedEvent value) {
//    System.out.println("payback-consumer is consumeForSsgPoints: " + value.getOrderKey());
//    System.out.println("payback-consumer is consumeForSsgPoints: " + value.getTxKey());
//    //ssgpoint
//  }
//
//}
