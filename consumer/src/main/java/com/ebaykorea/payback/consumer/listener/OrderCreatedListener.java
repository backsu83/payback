
package com.ebaykorea.payback.consumer.listener;


import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;

import javax.validation.Valid;

import com.ebaykorea.payback.consumer.service.RequestCashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("order-created")
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedListener {

  private final RequestCashbackService requestCashbackService;

  @KafkaListener(
      topics = "${kafka.consumer.topic.gmarket.order-created-event}",
      groupId = "${payback.consumers.order-created-cashback-listener.group-id}",
      concurrency = "${kafka.consumer.concurrency.gmarket.order-created}",
      containerFactory = "kafkaListenerContainerFactory2"
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
      concurrency = "${kafka.consumer.concurrency.gmarket-global.order-created}",
      containerFactory = "kafkaListenerContainerFactory2"
  )
  public void consumeForGlobalCashbacks(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : 'txKey: {},' ' orderKey: {}'",
        orderCreatedEvent.getTxKey(),
        orderCreatedEvent.getOrderKey());

    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
  }
}
