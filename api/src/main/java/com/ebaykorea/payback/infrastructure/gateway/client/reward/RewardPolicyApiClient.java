package com.ebaykorea.payback.infrastructure.gateway.client.reward;

import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyDto;
import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.RewardPolicyRequestDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    value = "rewardPolicyApiClient",
    url = "${apis.reward-policy.url}",
    configuration = DefaultFeignConfig.class
)
public interface RewardPolicyApiClient {

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/policies"
  )
  Optional<RewardPolicyDto> findRewardPolicy(@RequestBody RewardPolicyRequestDto request);
}
