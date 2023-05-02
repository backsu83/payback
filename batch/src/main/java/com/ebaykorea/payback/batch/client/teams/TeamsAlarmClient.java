package com.ebaykorea.payback.batch.client.teams;

import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamsAlarmClient {

  public static final String TEAMS_BATCH_PATH = "/webhookb2/f2cf4f9f-9e3a-41a4-82e1-c781182279bf@4d67598d-16bc-42f1-a18d-e8fb794aedca/IncomingWebhook/e0000f331ac64fc5a9f10cb2ae9975ac/4e33ec8e-01d9-4b42-a1e3-4b83a53b4a9b";
  private final TeamsWebClient teamsWebClient;


  public Mono<String> send(TeamsMessageDto message) {
    var webcleint = teamsWebClient.client()
        .method(HttpMethod.POST)
        .uri(uriBuilder ->
            uriBuilder.path(TEAMS_BATCH_PATH).build()
        );
    webcleint.body(Mono.just(message), TeamsMessageDto.class);

    return teamsWebClient.exchanged(webcleint, new ParameterizedTypeReference<String>() {
    });
  }

  public String sendSync(TeamsMessageDto message) {
    var response = teamsWebClient.client()
        .method(HttpMethod.POST)
        .uri(uriBuilder ->
            uriBuilder.path(TEAMS_BATCH_PATH).build()
        )
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(message), TeamsMessageDto.class)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    return response;
  }
}
