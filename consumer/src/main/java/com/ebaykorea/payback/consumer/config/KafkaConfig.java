package com.ebaykorea.payback.consumer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig implements KafkaListenerConfigurer {

  private final LocalValidatorFactoryBean validator;

  private final KafkaProperties kafkaProperties;

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;
  @Value("${spring.kafka.consumer.auto-offset-reset}")
  private String autoOffsetReset;
  @Value("${spring.kafka.consumer.enable-auto-commit}")
  private boolean enableAutoCommit;

  @Value("${spring.kafka.consumer.bootstrap-servers.listener-1}")
  private String bootstrapServers1;
  @Value("${spring.kafka.consumer.bootstrap-servers.listener-2}")
  private String bootstrapServers2;



  @Bean(name = "kafkaListenerContainerFactory1")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory1(
      @Qualifier(value = "consumerFactory2") ConsumerFactory<String, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setMessageConverter(new StringJsonMessageConverter());
    return factory;
  }

  @Bean(name = "kafkaListenerContainerFactory2")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory2(
      @Qualifier(value = "consumerFactory2") ConsumerFactory<String, String> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setMessageConverter(new StringJsonMessageConverter());
    return factory;
  }

  @Bean(name = "consumerFactory1")
  public ConsumerFactory<String, String> consumerFactory1(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
    final var factory = new DefaultKafkaConsumerFactory<>(
        configs(bootstrapServers1),
        new ErrorHandlingDeserializer<>(new StringDeserializer()),
        new ErrorHandlingDeserializer<>(new StringDeserializer()));
    customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
    return factory;
  }

  @Bean(name = "consumerFactory2")
  public ConsumerFactory<String, String> consumerFactory2(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
    final var factory = new DefaultKafkaConsumerFactory<>(
        configs(bootstrapServers2),
        new ErrorHandlingDeserializer<>(new StringDeserializer()),
        new ErrorHandlingDeserializer<>(new StringDeserializer()));
    customizers.orderedStream().forEach((customizer) -> customizer.customize(factory));
    return factory;
  }

  private Map<String, Object> configs(final String bootstrapServers) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    return props;
  }

  @Override
  public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
    registrar.setValidator(this.validator);
  }

  @Bean
  public KafkaListenerErrorHandler kafkaErrorHandler() {
    return (m, e) -> {
      /**
       * error 로그 기록
       */
      log.error("[KafkaErrorHandler] kafkaMessage=[" + m.getPayload() + "], errorMessage=[" + e.getMessage() + "]");
      return null;
    };
  }
}

