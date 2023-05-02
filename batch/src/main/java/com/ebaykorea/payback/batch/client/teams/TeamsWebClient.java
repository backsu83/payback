package com.ebaykorea.payback.batch.client.teams;

import com.ebaykorea.payback.batch.config.WebClientConfig;
import com.ebaykorea.payback.batch.domain.exception.PaybackException;
import com.ebaykorea.payback.batch.domain.exception.PaybackExceptionCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class TeamsWebClient extends WebClientConfig {

  @Value("${apis.teams.url}")
  private String teamsUrl;

  public WebClient client() {
    return webClient()
        .mutate()
        .baseUrl(teamsUrl)
        .build();
  }

  /**
   * Slack 웹 클라이언트 exchanged
   *
   * @param client        WebClient.RequestBodySpec
   * @param typeReference ParameterizedTypeReference<T>
   * @param <T>
   * @return <T> Mono
   */
  public <T> Mono<T> exchanged(WebClient.RequestBodySpec client,
      ParameterizedTypeReference<T> typeReference) {
    return client
        .retrieve()
        // 4xx and 5xx 처리
        .onStatus(
            status -> status.is4xxClientError(),
            response -> Mono.error(
                    new PaybackException(PaybackExceptionCode.API_GATEWAY_001,response.statusCode().getReasonPhrase()))
        )
        .onStatus(
            status -> status.is5xxServerError(),
            response -> Mono.error(
                new PaybackException(PaybackExceptionCode.API_GATEWAY_001,response.statusCode().getReasonPhrase()))
        )
        .bodyToMono(typeReference)
        .onErrorResume(WebClientResponseException.class,
            ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex));
  }
}
