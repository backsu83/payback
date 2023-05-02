package com.ebaykorea.payback.batch.client.teams;

import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import reactor.core.publisher.Mono;

public interface TeamsAlarmClient {

  Mono<String> send(TeamsMessageDto message);
  String sendSync(TeamsMessageDto message);
}
