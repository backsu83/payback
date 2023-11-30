package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCanceledAuctionEvent;
import com.ebaykorea.payback.consumer.service.CancelSsgPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Profile("auction & !av") //취소의 경우 av 환경 제외
@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionOrderCanceledListener {
  private final CancelSsgPointService requestSsgPointService;

  @KafkaListener(
      topics = "${payback.topic.order-canceled}",
      groupId = "${payback.consumers.order-canceled-ssgpoint-listener.group-id}",
      containerFactory = "kafkaListenerContainerFactory1",
      concurrency = "${payback.consumers.order-canceled-ssgpoint-listener.concurrency}"
  )
  public void consume(@Payload @Valid final OrderCanceledAuctionEvent event) {
    log.info("listener payload : [{}][{}]'",
        event.getOrderNo(),
        event.getPayNo()
    );
    requestSsgPointService.cancelSsgPoint(event.getPayNo(), List.of(event.getOrderNo()));
  }
}
