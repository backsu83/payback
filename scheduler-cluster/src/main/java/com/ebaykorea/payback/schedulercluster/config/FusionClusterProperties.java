package com.ebaykorea.payback.schedulercluster.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fusion.cluster.properties")
public class FusionClusterProperties {
  private int mod;
  private int modCount;
}
