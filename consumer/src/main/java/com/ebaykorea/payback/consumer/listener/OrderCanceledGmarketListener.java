package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCanceledGmarketEvent;
import com.ebaykorea.payback.consumer.event.OrderSiteType;
import com.ebaykorea.payback.consumer.service.RequestSsgPointService;
import com.ebaykorea.payback.consumer.util.GsonUtils;
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
public class OrderCanceledGmarketListener {

  private final RequestSsgPointService requestSsgPointService;
  private static final long CONSUME_FAIL = -2L;

  @KafkaListener(
      topics = "${payback.topic.order-canceled-gmkt}",
      groupId = "${payback.consumers.order-canceled-ssgpoint-gmkt-listener.group-id}",
      containerFactory = "kafkaListenerContainerFactory1"
  )
  public void consumeForSsgPoints(@Payload @Valid final OrderCanceledGmarketEvent event) {
    log.info("gmarket listener payload : [{}][{}][{}][{}]'",
        event.getRefundBundleKey() ,
        event.getPackNo(),
        event.getRegId(),
        GsonUtils.toJson(event.getContrNoList())
    );
    requestSsgPointService.cancelSsgPointGmarket(event.getPackNo() , event.getContrNoList());
  }

  @Bean
  public KafkaListenerErrorHandler consumeForSsgPointsErrorHandler() {
    return (m, e) -> {
      final var causedException = e.getCause();
      final var payload = (OrderCanceledGmarketEvent)m.getPayload();
      for (Long orderNo : payload.getContrNoList()) {
        requestSsgPointService.saveError(orderNo,
            payload.getPackNo(),
            OrderSiteType.Gmarket,
            CONSUME_FAIL,
            causedException.getMessage(),
            "OrderCanceledGmktListener");
      }
      log.error(causedException.getMessage(), causedException);
      return null;
    };
  }

}
