package com.ebaykorea.payback.port.gateway.client;

import com.ebaykorea.payback.adapter.persistence.redis.support.GsonUtils;
import com.ebaykorea.payback.port.gateway.client.RewardApiClient;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackResponseDataDto;
import com.ebaykorea.payback.port.gateway.ApiConfigTest;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackRequestDataDto;
import com.ebaykorea.payback.port.gateway.client.dto.RewardBaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest(classes = ApiConfigTest.class)
class RewardApiClientTest {

    @Autowired
    RewardApiClient rewardApiClient;

    @Test
    void cashbackReward() {

        String requestJson = "{\n" +
                "  \"Goods\": [\n" +
                "    {\n" +
                "      \"SiteCd\": 0,\n" +
                "      \"GdNo\": \"2433957171\",\n" +
                "      \"GdlcCd\": \"100000014\",\n" +
                "      \"GdmcCd\": \"200002344\",\n" +
                "      \"GdscCd\": \"300028902\",\n" +
                "      \"ScNo\": \"126361481\",\n" +
                "      \"IsSmileClub\": true,\n" +
                "      \"IsSmileDelivery\": false,\n" +
                "      \"Qty\": 1,\n" +
                "      \"Price\": 24800\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        final var cashbackReward = rewardApiClient.cashbackReward(GsonUtils.fromJson(requestJson, CashbackRequestDataDto.class));

        System.out.println(GsonUtils.toJsonPretty(cashbackReward));
    }
}