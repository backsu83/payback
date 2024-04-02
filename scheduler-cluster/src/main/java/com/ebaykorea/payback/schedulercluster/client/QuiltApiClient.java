package com.ebaykorea.payback.schedulercluster.client;

import com.ebaykorea.payback.schedulercluster.client.dto.member.QuiltBaseResponse;
import com.ebaykorea.payback.schedulercluster.config.DefaultFeignConfig;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
  Optional<QuiltBaseResponse<String>> findSmileUserKeyByMemberId(@RequestParam("memberId") final String memberId);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/smilecash/smileUserKey",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  Optional<QuiltBaseResponse<String>> findSmileUserKeyByCustNo(@RequestParam("custNo") final String CustNo);
}
