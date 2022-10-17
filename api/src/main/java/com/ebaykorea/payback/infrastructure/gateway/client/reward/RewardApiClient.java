package com.ebaykorea.payback.infrastructure.gateway.client.reward;

import com.ebaykorea.payback.infrastructure.gateway.client.reward.dto.*;
import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
  RewardBaseResponse<CashbackRewardResponseDto> getCashbackReward(@RequestBody final CashbackRewardRequestDto request);

  @RequestMapping(
      method = RequestMethod.POST,
      value = "api/Read/V2/CashbackRewardBackend",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  RewardBaseResponse<List<CashbackRewardBackendResponseDto>> getCashbackRewardBackend(@RequestBody final CashbackRewardRequestDto request);

  /**
   * 스마일카드 T2T3 캐시백 저장
   * @param request CartCashbackSaveRequestDto
   * @return RewardResponseDto 결과
   */
  @RequestMapping(
      method = RequestMethod.POST,
      value = "Cashback/v1/FE/Reward/Order/AddSmileCardT2T3Cashback",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  RewardBaseReturn saveCardT2T3Cashback(@RequestBody final AddSmileCardT2T3CashbackRequestDto request);

}
