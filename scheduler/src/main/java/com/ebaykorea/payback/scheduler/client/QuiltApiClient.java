package com.ebaykorea.payback.scheduler.client;

import com.ebaykorea.payback.scheduler.client.dto.member.QuiltBaseResponse;
import com.ebaykorea.payback.scheduler.config.DefaultFeignConfig;
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

  @RequestMapping(
      method = RequestMethod.GET,
      value = "${apis.quilt.user-id-query-path}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<QuiltBaseResponse<String>> findUserId(@RequestParam("userToken") final String userToken);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/smilecash/smileUserKey",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<QuiltBaseResponse<String>> findSmileUserKey(@RequestParam("memberId") final String memberId);
}
