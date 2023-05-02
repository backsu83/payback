package com.ebaykorea.payback.batch.client.teams.impl;

import com.ebaykorea.payback.batch.client.teams.BaseWebClient;
import com.ebaykorea.payback.batch.client.teams.TeamsAlarmClient;
import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Profile("teams")
@Component
@RequiredArgsConstructor
public class TeamsAlarmClientImpl implements TeamsAlarmClient {

  @Value("${apis.teams.url}")
  private String teamsUrl;

  @Value("${apis.teams.url.path-batch}")
  private String teamsPath;

  private final BaseWebClient baseWebClient;

  @Override
  public Mono<String> send(TeamsMessageDto message) {
    var webcleint = baseWebClient.client(teamsUrl)
        .method(HttpMethod.POST)
        .uri(uriBuilder ->
            uriBuilder.path(teamsPath).build()
        );
    webcleint.body(Mono.just(message), TeamsMessageDto.class);

    return baseWebClient.exchanged(webcleint, new ParameterizedTypeReference<String>() {
    });
  }

  @Override
  public String sendSync(final TeamsMessageDto message) {
    var response = baseWebClient.client(teamsUrl)
        .method(HttpMethod.POST)
        .uri(uriBuilder ->
            uriBuilder.path(teamsPath).build()
        )
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(message), TeamsMessageDto.class)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    return response;
  }
}
