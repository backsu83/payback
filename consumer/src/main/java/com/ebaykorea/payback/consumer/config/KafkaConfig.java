package com.ebaykorea.payback.consumer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig implements KafkaListenerConfigurer {

  private final LocalValidatorFactoryBean validator;

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
      ConsumerFactory<String, String> consumerFactory,
      @Value("${kafka.consumer.concurrency.order.created}") Integer concurrency
  ) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConcurrency(concurrency);
    factory.setConsumerFactory(consumerFactory);
    factory.setMessageConverter(new StringJsonMessageConverter());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory(
      KafkaProperties kafkaProperties,
      ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
    var factory = new DefaultKafkaConsumerFactory<>(
        kafkaProperties.buildConsumerProperties(),
        new ErrorHandlingDeserializer<>(new StringDeserializer()),
        new ErrorHandlingDeserializer<>(new StringDeserializer()));
    customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
    return factory;
  }

  @Override
  public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
    registrar.setValidator(this.validator);
  }
}

