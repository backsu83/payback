package com.ebaykorea.payback.scheduler.config.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

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
