
package com.ebaykorea.payback.consumer.listener;


import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;

import javax.validation.Valid;

import com.ebaykorea.payback.consumer.repository.CashbackOrderFailRepository;
import com.ebaykorea.payback.consumer.service.RequestCashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {

  private final RequestCashbackService requestCashbackService;

  private static final Logger moALogger = LoggerFactory.getLogger("MoALogger");

  @KafkaListener(
      topics = {"${payback.topic.order-created}_gmkt", "${payback.topic.order-created}_gg"},
      groupId = "${payback.consumers.order-created-cashback-listener.group-id}",
      errorHandler = "consumeForCashbacksErrorHandler"
  )
  public void consumeForCashbacks(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : '{}' '{}'",
        orderCreatedEvent.getOrderKey() ,
        orderCreatedEvent.getTxKey());

    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
  }

  @KafkaListener(
      topics = {"${payback.topic.order-created}_gmkt", "${payback.topic.order-created}_gg"},
      groupId = "${payback.consumers.order-created-ssgpoint-listener.group-id}",
      errorHandler = "consumeForSsgPointsErrorHandler"
  )
  public void consumeForSsgPoints(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : '{}' '{}'",
        orderCreatedEvent.getOrderKey() ,
        orderCreatedEvent.getTxKey());
    //ssgpoint
  }

  @Bean(name = "consumeForCashbacksErrorHandler")
  public KafkaListenerErrorHandler consumeForCashbacksErrorHandler() {
    return (m, e) -> {
      final var causedException = e.getCause();
      // TODO: DB 저장
      //requestCashbackService.saveError();

      // Moa Logging
      moALogger.error(causedException.getMessage(), causedException);
      log.error(causedException.getMessage(), causedException);

      return null;
    };
  }

  @Bean(name = "consumeForSsgPointsErrorHandler")
  public KafkaListenerErrorHandler consumeForSsgPointsErrorHandler() {
    return (m, e) -> {
      final var causedException = e.getCause();
      // TODO

      // Moa Logging
      moALogger.error(causedException.getMessage(), causedException);
      log.error(causedException.getMessage(), causedException);

      return null;
    };
  }
}
