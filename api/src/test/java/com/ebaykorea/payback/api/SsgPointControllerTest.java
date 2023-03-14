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


@AutoConfigureMockMvc
@SpringBootTest
class SsgPointControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("API 테스트")
  void saveSsgPointAuction() throws Exception {

    String requestJson = "{\n"
        + "  \"packNo\": \"12345677888\",\n"
        + "  \"orderNo\": \"12345677889\",\n"
        + "  \"buyerId\": \"buyerId\",\n"
        + "  \"siteType\": \"A\",\n"
        + "  \"tradeType\": \"SD\",\n"
        + "  \"payAmount\": \"1000\",\n"
        + "  \"orderDate\": \"2022-10-17T09:35:24.00Z\",\n"
        + "  \"scheduleDate\": \"2023-10-17T09:35:24.00Z\"\n"
        + "}";

    MockHttpServletRequestBuilder requestBuilder = post("/api/ssgpoint/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk());
  }
}