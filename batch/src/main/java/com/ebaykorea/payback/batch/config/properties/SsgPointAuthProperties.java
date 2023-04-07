package com.ebaykorea.payback.batch.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("ssgpoint.auth-info")
public class SsgPointAuthProperties {

  private Gmarket gmarket;
  private Auction auction;

  @Getter
  @Setter
  public static class Gmarket {
    private String memberKey;
    private String clientId;
    private String apiKey;
    private String encryptKey;
    private String encryptIv;
    private String branchId;
  }

  @Getter
  @Setter
  public static class Auction {
    private String memberKey;
    private String clientId;
    private String apiKey;
    private String encryptKey;
    private String encryptIv;
    private String branchId;
  }
}
