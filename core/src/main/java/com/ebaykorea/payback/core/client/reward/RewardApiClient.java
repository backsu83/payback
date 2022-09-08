package com.ebaykorea.payback.core.client.reward;

import com.ebaykorea.payback.core.client.reward.dto.CashbackRequestDto;
import com.ebaykorea.payback.core.client.reward.dto.CashbackResponseDto;
import com.ebaykorea.payback.core.client.reward.dto.RewardBaseResponse;
import com.ebaykorea.payback.core.config.client.DefaultFeignConfig;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        value = "rewardApiClient",
        url = "${apis.reward.url}",
        configuration = DefaultFeignConfig.class
)
public interface RewardApiClient {

    @Retry(name = "retryApi")
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/Read/V2/CashbackReward"
    )
    RewardBaseResponse<CashbackResponseDto> cashbackReward(CashbackRequestDto request);

}
