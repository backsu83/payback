package com.ebaykorea.payback.batch.client.ssgpoint;


import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.client.ssgpoint.dto.SsgPointAuthTokenResponse;
import com.ebaykorea.payback.batch.config.DefaultFeignConfig;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointCancelRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointEarnRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointCommonResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    name = "ssgPointApiClient",
    url = "${apis.ssgpoint.url}",
    configuration = DefaultFeignConfig.class
)
public interface SsgPointApiClient {

  @Retry(name = "retryApi")
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/auth",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointAuthTokenResponse getAuthToken(@RequestBody final SsgPointAuthTokenRequest request);

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/pntAdd",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointCommonResponse earnPoint(@RequestBody final SsgPointEarnRequest request);

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/pntAddCncl",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointCommonResponse cancelPoint(@RequestBody final SsgPointCancelRequest request);

  @RequestMapping(
          method = RequestMethod.POST,
          value = "/authapi/pntVerify",
          produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointVerifyResponse verifyPoint(@RequestBody final SsgPointVerifyRequest request);
}
