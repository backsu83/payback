package com.ebaykorea.payback.port.service.reward.client;

import com.ebaykorea.payback.adapter.persistence.redis.support.GsonUtils;
import com.ebaykorea.payback.port.service.ApiConfigTest;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackRequestDto;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackResponseDto;
import com.ebaykorea.payback.port.service.reward.client.dto.RewardBaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

        RewardBaseResponse<CashbackResponseDto> cashbackReward
                = rewardApiClient.cashbackReward(GsonUtils.fromJson(requestJson, CashbackRequestDto.class));

        System.out.println(GsonUtils.toJsonPretty(cashbackReward));
    }
}