package com.ebaykorea.payback.config.persistence.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class RewardAuditorAware implements AuditorAware<String> {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> auditor = Optional.of(appName);

        return auditor;
    }
}
