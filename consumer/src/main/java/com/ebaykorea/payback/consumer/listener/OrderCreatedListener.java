package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;
import com.ebaykorea.payback.consumer.service.RequestCashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Profile("gmarket")
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedListener {
  private final RequestCashbackService requestCashbackService;

  @KafkaListener(
      topics = {"${payback.topic.order-created}_gmkt", "${payback.topic.order-created}_gg"},
      groupId = "${payback.consumers.order-created-listener.group-id}",
      containerFactory = "kafkaListenerContainerFactory2",
      concurrency = "${payback.consumers.order-created-listener.concurrency}"
  )
  public void consume(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : 'txKey: {},' ' orderKey: {}'",
        orderCreatedEvent.getTxKey(),
        orderCreatedEvent.getOrderKey());

    requestCashbackService.requestCashback(orderCreatedEvent.getTxKey(), orderCreatedEvent.getOrderKey());
  }
}
