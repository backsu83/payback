package com.ebaykorea.payback.batch.client.teams;


import com.ebaykorea.payback.batch.client.teams.dto.TeamsMessageDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class TeamsAlarmClientTest {

  @Autowired
  TeamsAlarmClient teamsAlarmClient;

  @Test
  void send() {
    var request = TeamsMessageDto.builder()
        .title("배치결과")
        .text(String.format("\n[readCount:%d]\n[writeCount:%d]\n[duration:%d]", 1, 2, 3L))
        .build();
   teamsAlarmClient.send(request);

  }

  @Test
  void sendSync() {
    var request = TeamsMessageDto.builder()
        .title("배치결과")
        .text(String.format("\n[readCount:%d]\n[writeCount:%d]\n[duration:%d]", 1, 2, 3L))
        .build();
    teamsAlarmClient.sendSync(request);
  }
}