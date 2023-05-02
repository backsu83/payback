package com.ebaykorea.payback.batch.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import javax.net.ssl.SSLException;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


public class WebClientConfig {

  protected WebClient webClient() {
    final var sslContext = createInsecureSslContext();
    return WebClient.builder()
        .clientConnector(
            new ReactorClientHttpConnector(HttpClient.create()
                .secure(spec -> spec.sslContext(sslContext))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(3))
                        .addHandlerLast(new WriteTimeoutHandler(3)))
            )
        ).build();
  }

  private SslContext createInsecureSslContext() {
    try {
      return SslContextBuilder.forClient()
          .trustManager(InsecureTrustManagerFactory.INSTANCE)
          .build();
    } catch (SSLException e) {
      throw new RuntimeException("Failed to create SSL context", e);
    }
  }
}
