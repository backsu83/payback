package com.ebaykorea.payback.api;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import spock.lang.Ignore;

@Disabled
@AutoConfigureMockMvc
@SpringBootTest
class PayRewardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("API 테스트")
  void getCashbackReward() throws Exception {

    String requestJson = "{\n"
        + "  \"txKey\": \"170ef736ee000200t4thfgk\",\n"
        + "  \"orderKey\": \"170ef737618004006wqx8gk\"\n"
        + "}";

    MockHttpServletRequestBuilder requestBuilder = post("/api/cashbacks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk());
  }
}