
package com.ebaykorea.payback.consumer.listener;


import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;

import javax.validation.Valid;

import com.ebaykorea.payback.consumer.service.RequestCashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedListener {

  private final RequestCashbackService requestCashbackService;

  private static final long CONSUME_FAIL = -2L;

  @KafkaListener(
      topics = "${kafka.consumer.topic.gmarket.order-created-event}",
      groupId = "${payback.consumers.order-created-cashback-listener.group-id}",
      concurrency = "${kafka.consumer.concurrency.gmarket.order-created}"
      //errorHandler = "consumeForCashbacksErrorHandler" //TODO
  )
  public void consumeForCashbacks(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : 'txKey: {},' ' orderKey: {}'",
        orderCreatedEvent.getTxKey(),
        orderCreatedEvent.getOrderKey());

    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
  }

  @KafkaListener(
      topics = "${kafka.consumer.topic.gmarket-global.order-created-event}",
      groupId = "${payback.consumers.order-created-cashback-listener.group-id}",
      concurrency = "${kafka.consumer.concurrency.gmarket-global.order-created}"
      //errorHandler = "consumeForCashbacksErrorHandler" //TODO
  )
  public void consumeForGlobalCashbacks(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : 'txKey: {},' ' orderKey: {}'",
        orderCreatedEvent.getTxKey(),
        orderCreatedEvent.getOrderKey());

    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
  }

  @Bean(name = "consumeForCashbacksErrorHandler")
  public KafkaListenerErrorHandler error() {
    return (m, e) -> {
      final var causedException = e.getCause();
      final var payload = (OrderCreatedEvent)m.getPayload();

      requestCashbackService.saveError(
          payload.getTxKey(),
          payload.getOrderKey(),
          CONSUME_FAIL,
          causedException.getMessage(),
          "OrderCreatedEventListener");

      log.error(causedException.getMessage(), causedException);

      return null;
    };
  }

/* TODO
  @KafkaListener(
      topics = {"${kafka.consumer.topic.gmarket.order-created-event}", "${kafka.consumer.topic.gmarket-global.order-created-event}"},
      groupId = "${payback.consumers.order-created-ssgpoint-listener.group-id}",
      errorHandler = "consumeForSsgPointsErrorHandler"
  )
  public void consumeForSsgPoints(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : '{}' '{}'",
        orderCreatedEvent.getOrderKey() ,
        orderCreatedEvent.getTxKey());
    //ssgpoint
  }

  @Bean(name = "consumeForSsgPointsErrorHandler")
  public KafkaListenerErrorHandler consumeForSsgPointsErrorHandler() {
    return (m, e) -> {
      final var causedException = e.getCause();
      // TODO

      log.error(causedException.getMessage(), causedException);
      return null;
    };
  }

 */
}
