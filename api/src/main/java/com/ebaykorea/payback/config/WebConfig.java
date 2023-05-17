package com.ebaykorea.payback.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
    return customizer -> customizer.addConnectorCustomizers(connector -> {
      connector.setAllowTrace(true);  // filtered in the SecurityFilter with custom error
    });
  }
}
