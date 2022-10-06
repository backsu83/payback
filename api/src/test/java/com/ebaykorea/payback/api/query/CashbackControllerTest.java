package com.ebaykorea.payback.api.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CashbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("API조회 테스트")
    void getCashbackReward() throws Exception {

        String requestJson = "{\n" +
                "  \"Goods\": [\n" +
                "    {\n" +
                "      \"SiteCd\": \"0\",\n" +
                "      \"GdNo\": \"2433957171\",\n" +
                "      \"GdlcCd\": \"100000014\",\n" +
                "      \"GdmcCd\": \"200002344\",\n" +
                "      \"GdscCd\": \"300028902\",\n" +
                "      \"ScNo\": \"126361481\",\n" +
                "      \"IsSmileClub\": true,\n" +
                "      \"IsSmileDelivery\": false,\n" +
                "      \"IsSmileFresh\": false,\n" +
                "      \"Qty\": 1,\n" +
                "      \"Price\": 24800\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = post("/api/CashbackReward")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("API + DB조회 테스트")
    void getCashbackRewardOrder() throws Exception {

        String requestJson = "{\n" +
                "  \"BuyOrderNo\": 4681185340,\n" +
                "  \"CashbackType\": \"string\",\n" +
                "  \"Reward\": {\n" +
                "  \"TotalPrice\": 100,\n" +
                "  \"Goods\": [\n" +
                "    {\n" +
                "      \"SiteCd\": 0,\n" +
                "      \"GdNo\": \"1100342922\",\n" +
                "      \"GdlcCd\": \"100000003\",\n" +
                "      \"GdmcCd\": \"200000498\",\n" +
                "      \"GdscCd\": \"300005739\",\n" +
                "      \"ScNo\": \"100790651\",\n" +
                "      \"IsSmileClub\": true,\n" +
                "      \"IsSmileDelivery\": true,\n" +
                "      \"Qty\": 1,\n" +
                "      \"Price\": 100\n" +
                "    }\n" +
                "  ]\n" +
                "},\n" +
                "  \"TradeCd\": \"string\"\n" +
                "}";

        MockHttpServletRequestBuilder requestBuilder = post("/api/CashbackReward/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}