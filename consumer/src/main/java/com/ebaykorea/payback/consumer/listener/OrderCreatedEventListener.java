
package com.ebaykorea.payback.consumer.listener;


import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {

  @KafkaListener(
      topics = {"${payback.topic.order-created}_gmkt", "${payback.topic.order-created}_gg"},
      groupId = "${payback.consumers.order-created-cashback-listener.group-id}",
      errorHandler = "orderCreatedListenerErrorHandler"
  )
  public void consume(@Payload @Valid final OrderCreatedEvent orderCreatedEvent) {
    log.info("listener payload : '{}' '{}'",
        orderCreatedEvent.getOrderKey() ,
        orderCreatedEvent.getTxKey());
    
  }

  @Bean(name = "orderCreatedListenerErrorHandler")
  public KafkaListenerErrorHandler error() {

  }
}
