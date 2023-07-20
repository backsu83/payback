package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCanceledGmarketEvent;
import com.ebaykorea.payback.consumer.service.CancelSsgPointService;
import com.ebaykorea.payback.consumer.util.GsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Profile("gmarket & !av") //취소의 경우 av 환경 제외
@Slf4j
@Component
@RequiredArgsConstructor
public class GmarketOrderCanceledListener {
  private final CancelSsgPointService requestSsgPointService;

  @KafkaListener(
      topics = "${payback.topic.order-canceled}",
      groupId = "${payback.consumers.order-canceled-ssgpoint-listener.group-id}",
      containerFactory = "kafkaListenerContainerFactory1",
      concurrency = "${payback.consumers.order-canceled-ssgpoint-listener.concurrency}"
  )
  public void consume(@Payload @Valid final OrderCanceledGmarketEvent event) {
    log.info("listener payload : [{}][{}]'",
        GsonUtils.toJson(event.getContrNoList()),
        event.getPackNo()
    );
    requestSsgPointService.cancelSsgPoint(event.getPackNo(), event.getContrNoList());
  }
}
