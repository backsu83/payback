package com.ebaykorea.payback.consumer.listener;

import com.ebaykorea.payback.consumer.event.OrderCanceledGmarketEvent;
import com.ebaykorea.payback.consumer.service.RequestSsgPointService;
import com.ebaykorea.payback.consumer.util.GsonUtils;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCanceledGmarketListener {

  private final RequestSsgPointService requestSsgPointService;

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
}
