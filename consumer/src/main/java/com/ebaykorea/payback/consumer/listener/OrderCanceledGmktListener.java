package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.RefundCompletedEvent;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCanceledGmktListener {

  @KafkaListener(
      topics = {"${payback.topic.order-canceled-gmkt}"},
      groupId = "${payback.consumers.order-canceled-ssgpoint-gmkt-listener.group-id}"
  )
  public void consumeForSsgPoints(@Payload @Valid final RefundCompletedEvent refundCompletedEvent) {
    System.out.println("payback-consumer is consumeForSsgPoints");
    log.info("listener payload : [{}][{}][{}][{}]'",
        refundCompletedEvent.getRefundBundleKey() ,
        refundCompletedEvent.getPackNo(),
        refundCompletedEvent.getRegId(),
        refundCompletedEvent.getContrNoList().size()
    );
    //ssgpoint
  }

  @Bean
  public KafkaListenerErrorHandler consumeForSsgPointsErrorHandler() {
    return (m, e) -> {
      final var causedException = e.getCause();
      final var payload = (RefundCompletedEvent)m.getPayload();
      log.info("listener payload : [{}][{}][{}][{}]'",
          payload.getRefundBundleKey() ,
          payload.getPackNo(),
          payload.getRegId(),
          payload.getContrNoList().size()
      );
      log.error(causedException.getMessage(), causedException);
      return null;
    };
  }

}
