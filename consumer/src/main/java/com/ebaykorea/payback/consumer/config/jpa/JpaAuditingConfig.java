package com.ebaykorea.payback.consumer.config.jpa;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "paybackAuditorAware",
    dateTimeProviderRef = "paybackDateTimeProvider"
)
public class JpaAuditingConfig {

  @Bean("paybackAuditorAware")
  public AuditorAware<String> auditorAware(
      @Value("${spring.application.name}") final String appName) {
    return () -> Optional.of(appName);
  }

  @Bean("paybackDateTimeProvider")
  @ConditionalOnMissingBean
  public DateTimeProvider dateTimeProvider() {
    return CurrentDateTimeProvider.INSTANCE;
  }
}
