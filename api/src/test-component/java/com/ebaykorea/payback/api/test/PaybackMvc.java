package com.ebaykorea.payback.api.test;

import com.ebaykorea.payback.api.dto.common.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
@RequiredArgsConstructor
public class PaybackMvc {
  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  public CommonResponse saveCashback(final String requestJson) throws Exception {
    final MvcResult saveCashbackMvcResult =
        mockMvc
            .perform(
                post("/v1/api/cashbacks").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isCreated())
            .andReturn();

    return objectMapper.readValue(
        saveCashbackMvcResult.getResponse().getContentAsString(), CommonResponse.class);
  }
}
