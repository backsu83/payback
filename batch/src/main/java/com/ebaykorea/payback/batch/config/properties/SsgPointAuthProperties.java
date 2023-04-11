package com.ebaykorea.payback.batch.config.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@AllArgsConstructor
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

    public Gmarket(final String memberKey,
        final String clientId,
        final String apiKey,
        final String encryptKey,
        final String encryptIv,
        final String branchId)
    {
      this.memberKey = memberKey;
      this.clientId = clientId;
      this.apiKey = apiKey;
      this.encryptKey = encryptKey;
      this.encryptIv = encryptIv;
      this.branchId = branchId;
    }
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

    public Auction(final String memberKey,
        final String clientId,
        final String apiKey,
        final String encryptKey,
        final String encryptIv,
        final String branchId)
    {
      this.memberKey = memberKey;
      this.clientId = clientId;
      this.apiKey = apiKey;
      this.encryptKey = encryptKey;
      this.encryptIv = encryptIv;
      this.branchId = branchId;
    }
  }
}

