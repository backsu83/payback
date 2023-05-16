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
  void earnSsgPointTest() throws Exception {

    String requestJson = "{\n"
        + "  \"packNo\": \"12345677888\",\n"
        + "  \"orderNo\": \"12345677889\",\n"
        + "  \"buyerId\": \"buyerId\",\n"
        + "  \"siteType\": \"A\",\n"
        + "  \"tradeType\": \"S\",\n"
        + "  \"payAmount\": \"1000\",\n"
        + "  \"saveAmount\": \"1000\",\n"
        + "  \"orderDate\": \"2022-10-17 09:35:24\",\n"
        + "  \"adminId\": \"test\",\n"
        + "  \"scheduleDate\": \"2023-10-17 00:00:00\"\n"
        + "}";

    MockHttpServletRequestBuilder requestBuilder = post("/api/ssg-points")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void cancelSsgPointTest() throws Exception {

    String requestJson = "{\n"
        + "  \"packNo\": \"788898608\",\n"
        + "  \"buyerId\": \"109543617\",\n"
        + "  \"siteType\": \"G\"\n"
        + "}";

    MockHttpServletRequestBuilder requestBuilder = post("/api/ssg-points/7888998608/cancel")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk());
  }
}