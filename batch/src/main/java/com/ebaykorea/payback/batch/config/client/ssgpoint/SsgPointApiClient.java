package com.ebaykorea.payback.batch.config.client.ssgpoint;


import com.ebaykorea.payback.batch.config.DefaultFeignConfig;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointAuthTokenRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointAuthTokenResponse;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointEarnRequest;
import com.ebaykorea.payback.batch.config.client.ssgpoint.dto.SsgPointResponse;
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

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/auth",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointAuthTokenResponse getAuthToken(@RequestBody final SsgPointAuthTokenRequest request);


  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/pntAddCino",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointResponse earnPoint(@RequestBody final SsgPointEarnRequest request);

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/authapi/PntAddCnclCino",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  SsgPointResponse cancelPoint(@RequestBody final SsgPointEarnRequest request);



}
