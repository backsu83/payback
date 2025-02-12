package com.ebaykorea.payback.consumer;



import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ebaykorea.payback.consumer.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@Disabled
@SpringBootTest
@EmbeddedKafka(partitions = 1,
    topics = "${payback.topic.order-created}_gmkt",
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092"
    },
    ports = { 9092 })
public class EmbeddedKafkaIntegrationTest {

  @Autowired
  EmbeddedKafkaBroker embeddedKafkaBroker;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  ConsumerFactory<String, String> consumerFactory;

  @Autowired
  KafkaTemplate kafkaTemplate;

  OrderCreatedEvent consumer;

  public static final String TOPIC = "bz_carbon_order_created_gmkt";

  @Test
  void testKafkaTopic() throws Exception {

    OrderCreatedEvent event = OrderCreatedEvent.builder()
        .orderKey("orderKey")
        .txKey("txKey")
        .build();
    final var message = objectMapper.writeValueAsString(event);

    final var consumer = consumerFactory.createConsumer("order-gmkt-group", "test");
    embeddedKafkaBroker.consumeFromEmbeddedTopics(consumer, TOPIC);
    kafkaTemplate.send(new ProducerRecord<>(TOPIC, 0, null, message));

    var record = KafkaTestUtils.getSingleRecord(consumer, TOPIC, 10000);
    var eventMessage = (new StringJsonMessageConverter()).toMessage(
        record, null, null, OrderCreatedEvent.class
    );
    var consumedEvent = (OrderCreatedEvent) eventMessage.getPayload();

    assertEquals(event.getOrderKey() , consumedEvent.getOrderKey());
    assertEquals(event.getTxKey() , consumedEvent.getTxKey());
  }
}
