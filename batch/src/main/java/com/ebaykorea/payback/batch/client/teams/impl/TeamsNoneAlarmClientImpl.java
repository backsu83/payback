package com.ebaykorea.payback.batch.client.teams.impl;

import com.ebaykorea.payback.batch.client.teams.TeamsAlarmClient;
import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Profile("!teams")
@Component
@RequiredArgsConstructor
public class TeamsNoneAlarmClientImpl implements TeamsAlarmClient {

    @Override
    public Mono<String> send(TeamsMessageDto message) {
        return Mono.empty();
    }

    @Override
    public String sendSync(TeamsMessageDto message) {
        return null;
    }
}
