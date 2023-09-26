package com.ebaykorea.payback.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("saturn.application")
@Getter
@Setter
public class SaturnApplicationProperties {
  private String tenantId;
}