package com.ebaykorea.payback.scheduler.config.properties;

import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("saturn.application.api-info")
@Getter
@Setter
public class ApiInfoProperties {
  private String name;
  private String apiPackage;
  @Valid
  private Version version = new Version();
  @Getter
  @Setter
  public static class Version {
    private String major;
    private String full;
  }
}
