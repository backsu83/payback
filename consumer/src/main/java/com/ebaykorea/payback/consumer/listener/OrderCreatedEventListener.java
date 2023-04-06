//
//package com.ebaykorea.payback.consumer.listener;
//
//
//import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;
//import com.ebaykorea.payback.consumer.service.RequestCashbackService;
//import javax.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.listener.KafkaListenerErrorHandler;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class OrderCreatedEventListener {
//
//  private final RequestCashbackService requestCashbackService;
//
//  private static final long CONSUME_FAIL = -2L;
//
//  @KafkaListener(
//      topics = {"${payback.topic.order-created}_gmkt", "${payback.topic.order-created}_gg"},
//      groupId = "${payback.consumers.order-created-cashback-listener.group-id}"
//  )
//  public void consumeForCashbacks(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
//    log.info("listener payload : '{}' '{}'",
//        orderCreatedEvent.getOrderKey() ,
//        orderCreatedEvent.getTxKey());
//
//    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
//  }
//
//  @Bean(name = "consumeForCashbacksErrorHandler")
//  public KafkaListenerErrorHandler consumeForCashbacksErrorHandler() {
//    return (m, e) -> {
//      final var causedException = e.getCause();
//      final var payload = (OrderCreatedEvent)m.getPayload();
//
//      requestCashbackService.saveError(
//          payload.getTxKey(),
//          payload.getOrderKey(),
//          CONSUME_FAIL,
//          causedException.getMessage(),
//          "OrderCreatedEventListener");
//
//      log.error(causedException.getMessage(), causedException);
//      return null;
//    };
//  }
//}
