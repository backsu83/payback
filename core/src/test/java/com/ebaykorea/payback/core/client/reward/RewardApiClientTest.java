package com.ebaykorea.payback.core.client.reward;

import com.ebaykorea.payback.core.client.reward.dto.CashbackRequestDto;
import com.ebaykorea.payback.core.client.reward.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.client.reward.dto.RewardBaseResponse;
import com.ebaykorea.payback.core.common.support.GsonUtils;
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