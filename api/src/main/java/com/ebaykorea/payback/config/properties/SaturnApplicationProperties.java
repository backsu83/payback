package com.ebaykorea.payback.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.ebaykorea.payback.core.domain.constant.TenantCode.GMARKET_TENANT;
import static com.ebaykorea.payback.util.PaybackStrings.orEmpty;

@Component
@ConfigurationProperties("saturn.application")
@Getter
@Setter
public class SaturnApplicationProperties {
  private String tenantId;

  private static final String GMARKET = "G";
  private static final String AUCTION = "A";

  public String getSiteCode() {
    return orEmpty(tenantId).equalsIgnoreCase(GMARKET_TENANT) ? GMARKET : AUCTION;
  }
}