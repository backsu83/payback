package com.ebaykorea.payback.infrastructure.gateway.client.member;

import com.ebaykorea.payback.infrastructure.gateway.client.config.DefaultFeignConfig;
import com.ebaykorea.payback.infrastructure.gateway.client.member.dto.QuiltBaseResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
    value = "quiltApiClient",
    url = "${apis.quilt.url}",
    configuration = DefaultFeignConfig.class
)
public interface QuiltApiClient {
  @Retry(name = "retryApi")
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/smilecash/smileUserKey",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<QuiltBaseResponse<String>> findUserKey(@RequestParam("custNo") final String custNo);
}
