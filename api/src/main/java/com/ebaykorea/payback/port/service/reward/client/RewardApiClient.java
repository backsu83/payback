package com.ebaykorea.payback.port.service.reward.client;

import com.ebaykorea.payback.port.service.config.DefaultFeignConfig;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackRequestDto;
import com.ebaykorea.payback.port.service.reward.client.dto.CashbackResponseDto;
import com.ebaykorea.payback.port.service.reward.client.dto.RewardBaseResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
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
  RewardBaseResponse<CashbackResponseDto> cashbackReward(@RequestBody CashbackRequestDto request);

}
