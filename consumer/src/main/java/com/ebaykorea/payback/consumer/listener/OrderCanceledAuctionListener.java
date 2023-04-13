package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCanceledAuctionEvent;
import com.ebaykorea.payback.consumer.event.OrderSiteType;
import com.ebaykorea.payback.consumer.service.RequestSsgPointService;
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
public class OrderCanceledAuctionListener {

  private final RequestSsgPointService requestSsgPointService;
  private static final long CONSUME_FAIL = -2L;

  @KafkaListener(
      topics = "${payback.topic.order-canceled-iac}",
      groupId = "${payback.consumers.order-canceled-ssgpoint-iac-listener.group-id}",
      containerFactory = "kafkaListenerContainerFactory1"
  )
  public void consumeForSsgPoints(@Payload @Valid final OrderCanceledAuctionEvent event) {
    log.info("auction listener payload : [{}][{}]'",
        event.getOrderNo() ,
        event.getPayNo()
    );
    requestSsgPointService.cancelSsgPointAuction(event.getPayNo() , event.getOrderNo());
  }

  @Bean
  public KafkaListenerErrorHandler consumeForSsgPointsErrorHandler() {
    return (m, e) -> {
      final var exceptionMessage = e.getLocalizedMessage();
      final var payload = (OrderCanceledAuctionEvent)m.getPayload();
      requestSsgPointService.saveError(payload.getOrderNo(),
          payload.getPayNo(),
          OrderSiteType.Auction,
          CONSUME_FAIL,
          exceptionMessage,
          "OrderCanceledAuctionListener");
      log.error(exceptionMessage , e.getCause());
      return null;
    };
  }
}
