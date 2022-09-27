package com.ebaykorea.payback.port.gateway.client;

import com.ebaykorea.payback.port.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackRequestDataDto;
import com.ebaykorea.payback.port.gateway.client.dto.CashbackResponseDataDto;
import com.ebaykorea.payback.port.gateway.client.dto.RewardBaseResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

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
  RewardBaseResponse<CashbackResponseDataDto> cashbackReward(@RequestBody CashbackRequestDataDto request);

}
